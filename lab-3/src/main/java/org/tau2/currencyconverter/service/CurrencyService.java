package org.tau2.currencyconverter.service;

import org.tau2.currencyconverter.transferObject.CurrencyExchange;
import org.tau2.currencyconverter.transferObject.CurrencyExchangeRequest;
import org.tau2.currencyconverter.transferObject.NewCurrencyExchange;

import java.util.List;

public interface CurrencyService {
	double convertMoney(CurrencyExchangeRequest currencyExchangeRequest);

	List<CurrencyExchange> getAllCurrencies();

	void save(NewCurrencyExchange currencyExchange);
}
