package org.tau2.currencyconverter.service;

import org.tau2.currencyconverter.exception.NoSuchCurrencyExchangeException;
import org.tau2.currencyconverter.model.CurrencyExchangeEntity;
import org.tau2.currencyconverter.repository.CurrencyRepository;
import org.tau2.currencyconverter.transferObject.CurrencyExchangeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CurrencyServiceImplTest {

	@Mock
	CurrencyRepository currencyRepository;
	@InjectMocks
	CurrencyServiceImpl currencyService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void convertMoney_should_returnCalculatedValue_when_currencyExchangeExists() {
		CurrencyExchangeEntity currencyExchangeEntity = new CurrencyExchangeEntity("EUR", "PLN", 4.45);
		CurrencyExchangeRequest currencyExchangeRequest = new CurrencyExchangeRequest("EUR", "PLN", 100);
		when(currencyRepository.findByFromCodeAndToCode(currencyExchangeRequest.getFromCode(), currencyExchangeRequest.getToCode()))
				.thenReturn(currencyExchangeEntity);
		double expected = currencyExchangeEntity.getConvertRate() * currencyExchangeRequest.getAmount();

		double res = currencyService.convertMoney(currencyExchangeRequest);
		assertEquals(expected, res);
	}

	@Test
	void convertMoney_should_throwException_when_currencyExchangeDoesntExist() {
		when(currencyRepository.findByFromCodeAndToCode(anyString(), anyString())).thenReturn(null);

		assertThrows(NoSuchCurrencyExchangeException.class, () -> {
			currencyService.convertMoney(new CurrencyExchangeRequest("PLN", "EUR", 100));
		});
	}
}
