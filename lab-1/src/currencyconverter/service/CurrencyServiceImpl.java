package currencyconverter.service;

import currencyconverter.exception.NoSuchCurrencyExchangeException;
import currencyconverter.model.CurrencyExchangeEntity;
import currencyconverter.repository.CurrencyRepository;
import currencyconverter.transferObject.CurrencyExchangeRequest;

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
