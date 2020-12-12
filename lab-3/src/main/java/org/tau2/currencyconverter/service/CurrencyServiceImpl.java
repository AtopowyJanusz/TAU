package org.tau2.currencyconverter.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.tau2.currencyconverter.exception.NoSuchCurrencyExchangeException;
import org.tau2.currencyconverter.model.CurrencyExchangeEntity;
import org.tau2.currencyconverter.repository.CurrencyExchangeRepository;
import org.tau2.currencyconverter.transferObject.CurrencyExchange;
import org.tau2.currencyconverter.transferObject.CurrencyExchangeRequest;
import org.tau2.currencyconverter.transferObject.NewCurrencyExchange;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

	private final CurrencyExchangeRepository currencyExchangeRepository;
	private final ModelMapper modelMapper;

	public CurrencyServiceImpl(CurrencyExchangeRepository currencyExchangeRepository, ModelMapper modelMapper) {
		this.currencyExchangeRepository = currencyExchangeRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public double convertMoney(CurrencyExchangeRequest currencyExchangeRequest) {
		CurrencyExchangeEntity currencyExchangeEntity = this.currencyExchangeRepository.findByFromCodeAndToCode(
				currencyExchangeRequest.getFromCode(), currencyExchangeRequest.getToCode()
		);
		if (currencyExchangeEntity == null) {
			String message = "Provided exchange doesn't exist in our database: " +
					currencyExchangeRequest.getFromCode() +
					" - " +
					currencyExchangeRequest.getToCode();
			throw new NoSuchCurrencyExchangeException(message);
		}
		return currencyExchangeEntity.getConvertRate() * currencyExchangeRequest.getAmount();
	}

	@Override
	public List<CurrencyExchange> getAllCurrencies() {
		List<CurrencyExchange> currencies = new ArrayList<>();
		this.currencyExchangeRepository.findAll().forEach(c -> {
			CurrencyExchange currency = this.modelMapper.map(c, CurrencyExchange.class);
			currencies.add(currency);
		});
		return currencies;
	}

	@Override
	public void save(NewCurrencyExchange currencyExchange) {
		if (this.currencyExchangeRepository.existsByFromCodeAndToCode(currencyExchange.getFromCode(), currencyExchange.getToCode())) {
			throw new EntityExistsException(
					"Currency exchange already exists: " + currencyExchange.getFromCode() + " to " + currencyExchange.getToCode()
			);
		}
		this.currencyExchangeRepository.save(this.modelMapper.map(currencyExchange, CurrencyExchangeEntity.class));
	}
}
