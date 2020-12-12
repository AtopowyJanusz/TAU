package org.tau2.currencyconverter.service;

import org.tau2.currencyconverter.exception.NoSuchCurrencyExchangeException;
import org.tau2.currencyconverter.model.CurrencyExchangeEntity;
import org.tau2.currencyconverter.repository.CurrencyRepository;
import org.tau2.currencyconverter.transferObject.CurrencyExchangeRequest;

public class CurrencyServiceImpl implements CurrencyService {

	private final CurrencyRepository currencyRepository;

	public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
		this.currencyRepository = currencyRepository;
	}

	@Override
	public double convertMoney(CurrencyExchangeRequest currencyExchangeRequest) {
		CurrencyExchangeEntity currencyExchangeEntity = this.currencyRepository.findByFromCodeAndToCode(
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
}
