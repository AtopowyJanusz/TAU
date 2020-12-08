package currencyconverter.repository;

import currencyconverter.model.CurrencyExchangeEntity;

public interface CurrencyRepository {
	CurrencyExchangeEntity findByFromCodeAndToCode(String fromCode, String toCode);
}
