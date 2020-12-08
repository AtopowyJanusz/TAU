package currencyconverter.service;

import currencyconverter.transferObject.CurrencyExchangeRequest;

public interface CurrencyService {
	double convertMoney(CurrencyExchangeRequest currencyExchangeRequest);
}
