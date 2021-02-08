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
import org.tau2.currencyconverter.transferObject.NewRate;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

	@Test
	void update_should_returnFalse_when_currencyExchangeDoesNotExist() {
		when(currencyExchangeRepository.findById(anyLong())).thenReturn(Optional.empty());

		boolean res = currencyService.update(1L, new NewRate());
		assertFalse(res);
	}

	@Test
	void update_should_returnTrue_when_currencyExchangeDoesExist() {
		NewRate rate = new NewRate();
		rate.setRate(1.2d);
		when(currencyExchangeRepository.findById(anyLong())).thenReturn(Optional.of(new CurrencyExchangeEntity()));

		boolean res = currencyService.update(1L, rate);
		assertTrue(res);
	}

	@Test
	void findCurrencyExchange_should_returnNull_when_currencyExchangeDoesNotExist() {
		String fromCode = "PLN";
		String toCode = "EUR";
		when(currencyExchangeRepository.findByFromCodeAndToCode(anyString(), anyString())).thenReturn(null);

		CurrencyExchange res = currencyService.findCurrencyExchange(fromCode, toCode);
		assertNull(res);
	}

	@Test
	void findCurrencyExchange_should_returnCurrencyExchange_when_currencyExchangeDoesExist() {
		Long id = 1L;
		double rate = 1.0d;
		String fromCode = "PLN";
		String toCode = "EUR";
		CurrencyExchangeEntity currencyExchangeEntity = new CurrencyExchangeEntity();
		currencyExchangeEntity.setId(id);
		currencyExchangeEntity.setFromCode(fromCode);
		currencyExchangeEntity.setToCode(toCode);
		currencyExchangeEntity.setConvertRate(rate);
		CurrencyExchange currencyExchange = new CurrencyExchange();
		currencyExchange.setId(id);
		currencyExchange.setFromCode(fromCode);
		currencyExchange.setToCode(toCode);
		currencyExchange.setConvertRate(rate);
		when(currencyExchangeRepository.findByFromCodeAndToCode(anyString(), anyString())).thenReturn(currencyExchangeEntity);

		CurrencyExchange res = currencyService.findCurrencyExchange(fromCode, toCode);
		assertNotNull(res);
		assertEquals(currencyExchangeEntity.getId(), currencyExchange.getId());
		assertEquals(currencyExchangeEntity.getFromCode(), currencyExchange.getFromCode());
		assertEquals(currencyExchangeEntity.getToCode(), currencyExchange.getToCode());
		assertEquals(currencyExchangeEntity.getConvertRate(), currencyExchange.getConvertRate());
	}
}
