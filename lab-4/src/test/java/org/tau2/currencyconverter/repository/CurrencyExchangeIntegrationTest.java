package org.tau2.currencyconverter.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.tau2.currencyconverter.model.CurrencyExchangeEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class CurrencyExchangeIntegrationTest {

	@Autowired
	CurrencyExchangeRepository currencyExchangeRepository;
	List<CurrencyExchangeEntity> currencies;

	@BeforeEach
	void setup() {
		CurrencyExchangeEntity currencyExchangeEntity1 = new CurrencyExchangeEntity("EUR", "PLN", 4.45);
		CurrencyExchangeEntity currencyExchangeEntity2 = new CurrencyExchangeEntity("PLN", "EUR", 0.22);
		List<CurrencyExchangeEntity> currencyEntities = List.of(currencyExchangeEntity1, currencyExchangeEntity2);
		this.currencies = this.currencyExchangeRepository.saveAll(currencyEntities);
		this.currencyExchangeRepository.flush();
	}

	@AfterEach
	void tearDown() {
		this.currencyExchangeRepository.deleteAll();
	}

	@Test
	void findByFromCodeAndToCode_should_returnCurrencyExchangeEntity_when_currencyExchangeExists() {
		CurrencyExchangeEntity fromList = currencies.get(0);

		CurrencyExchangeEntity res = currencyExchangeRepository.findByFromCodeAndToCode(fromList.getFromCode(), fromList.getToCode());
		assertEquals(fromList.getId(), res.getId());
		assertEquals(fromList.getFromCode(), res.getFromCode());
		assertEquals(fromList.getToCode(), res.getToCode());
		assertEquals(fromList.getConvertRate(), res.getConvertRate());
	}

	@Test
	void findByFromCodeAndToCode_should_returnNull_when_currencyExchangeDoesntExist() {
		String fromCode = "this shouldnt exist";
		String toCode = "this shouldnt exist either";

		CurrencyExchangeEntity res = currencyExchangeRepository.findByFromCodeAndToCode(fromCode, toCode);
		assertNull(res);
	}

	@Test
	void existsByFromCodeAndToCode_should_returnTrue_when_currencyExchangeDoesExist() {
		CurrencyExchangeEntity fromList = currencies.get(0);

		boolean res = currencyExchangeRepository.existsByFromCodeAndToCode(fromList.getFromCode(), fromList.getToCode());
		assertTrue(res);
	}

	@Test
	void existsByFromCodeAndToCode_should_returnFalse_when_currencyExchangeDoesntExist() {
		String fromCode = "this shouldnt exist";
		String toCode = "this shouldnt exist either";

		boolean res = currencyExchangeRepository.existsByFromCodeAndToCode(fromCode, toCode);
		assertFalse(res);
	}
}
