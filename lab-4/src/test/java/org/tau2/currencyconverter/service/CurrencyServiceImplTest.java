package org.tau2.currencyconverter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.tau2.currencyconverter.exception.NoSuchCurrencyExchangeException;
import org.tau2.currencyconverter.model.CurrencyExchangeEntity;
import org.tau2.currencyconverter.repository.CurrencyExchangeRepository;
import org.tau2.currencyconverter.transferObject.CurrencyExchange;
import org.tau2.currencyconverter.transferObject.CurrencyExchangeRequest;
import org.tau2.currencyconverter.transferObject.NewCurrencyExchange;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CurrencyServiceImplTest {

	@Mock
	CurrencyExchangeRepository currencyExchangeRepository;
	@Spy
	ModelMapper modelMapper;
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
		when(currencyExchangeRepository.findByFromCodeAndToCode(currencyExchangeRequest.getFromCode(), currencyExchangeRequest.getToCode()))
				.thenReturn(currencyExchangeEntity);
		double expected = currencyExchangeEntity.getConvertRate() * currencyExchangeRequest.getAmount();

		double res = currencyService.convertMoney(currencyExchangeRequest);
		assertEquals(expected, res);
	}

	@Test
	void convertMoney_should_throwException_when_currencyExchangeDoesntExist() {
		when(currencyExchangeRepository.findByFromCodeAndToCode(anyString(), anyString())).thenReturn(null);

		assertThrows(NoSuchCurrencyExchangeException.class, () -> {
			currencyService.convertMoney(new CurrencyExchangeRequest("PLN", "EUR", 100));
		});
	}

	@Test
	void getAllCurrencies_should_returnEmptyCurrenciesList_when_thereAreNoCurrenciesInRepository() {
		List<CurrencyExchangeEntity> repositoryList = new ArrayList<>();
		when(currencyExchangeRepository.findAll()).thenReturn(repositoryList);

		List<CurrencyExchange> res = currencyService.getAllCurrencies();
		assertEquals(repositoryList.size(), res.size());
	}

	@Test
	void getAllCurrencies_should_returnPopulatedCurrenciesList_when_thereAreCurrenciesInRepository() {
		List<CurrencyExchangeEntity> repositoryList = List.of(
			new CurrencyExchangeEntity(1L, "PLN", "EUR", 0.22),
			new CurrencyExchangeEntity(2L, "EUR", "PLN", 4.45)
		);
		when(currencyExchangeRepository.findAll()).thenReturn(repositoryList);

		List<CurrencyExchange> res = currencyService.getAllCurrencies();
		assertEquals(repositoryList.size(), res.size());
	}

	@Test
	void save_should_callSaveFromRepositoryOnce() {
		when(currencyExchangeRepository.existsByFromCodeAndToCode(anyString(), anyString())).thenReturn(false);

		currencyService.save(new NewCurrencyExchange());
		verify(currencyExchangeRepository, times(1)).save(any(CurrencyExchangeEntity.class));
	}

	@Test
	void save_should_throwEntityExistsException_when_currencyExchangeAlreadyExistsInRepository() {
		NewCurrencyExchange currencyExchange = new NewCurrencyExchange();
		currencyExchange.setFromCode("PLN");
		currencyExchange.setToCode("EUR");
		when(currencyExchangeRepository.existsByFromCodeAndToCode(currencyExchange.getFromCode(), currencyExchange.getToCode()))
				.thenReturn(true);

		assertThrows(EntityExistsException.class, () -> {
			currencyService.save(currencyExchange);
		});
		verify(currencyExchangeRepository, times(0)).save(any(CurrencyExchangeEntity.class));
	}
}
