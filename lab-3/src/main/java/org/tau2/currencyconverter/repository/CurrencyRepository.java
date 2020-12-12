package org.tau2.currencyconverter.repository;

import org.tau2.currencyconverter.model.CurrencyExchangeEntity;

public interface CurrencyRepository {
	CurrencyExchangeEntity findByFromCodeAndToCode(String fromCode, String toCode);
}
