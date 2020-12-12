package org.tau2.currencyconverter.service;

import org.tau2.currencyconverter.transferObject.CurrencyExchangeRequest;

public interface CurrencyService {
	double convertMoney(CurrencyExchangeRequest currencyExchangeRequest);
}
