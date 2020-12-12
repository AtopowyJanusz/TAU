package org.tau2.currencyconverter.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CurrencyExchangeEntityTest {

	@Test
	void equals_should_returnTrue_whenCodesAreEqual() {
		CurrencyExchangeEntity cur1 = new CurrencyExchangeEntity("PLN", "EUR", 100);
		CurrencyExchangeEntity cur2 = new CurrencyExchangeEntity("PLN", "EUR", 200);

		boolean res = cur1.equals(cur2);
		assertTrue(res);
	}

	@Test
	void equals_should_returnFalse_whenCodesAreDifferent() {
		CurrencyExchangeEntity cur1 = new CurrencyExchangeEntity("PLN", "EUR", 100);
		CurrencyExchangeEntity cur2 = new CurrencyExchangeEntity("EUR", "PLN", 200);

		boolean res = cur1.equals(cur2);
		assertFalse(res);
	}
}
