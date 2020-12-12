package org.tau2.currencyconverter.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.tau2.currencyconverter.transferObject.NewCurrencyExchange;

import static org.junit.jupiter.api.Assertions.*;

class NewCurrencyExchangeValidatorTest {

	NewCurrencyExchange currency;
	NewCurrencyExchangeValidator validator;
	Errors errors;

	@BeforeEach
	void setup() {
		validator = new NewCurrencyExchangeValidator();
		currency = new NewCurrencyExchange();
		errors = new BeanPropertyBindingResult(currency, "newCurrencyExchange");
	}

	@Test
	void validate_should_notGenerateAnyErrorsWhenObjectIsValid() {
		this.currency.setFromCode("EUR");
		this.currency.setToCode("PLN");
		this.currency.setRate(4.45);

		this.validator.validate(currency, errors);
		assertFalse(errors.hasErrors());
	}

	@Test
	void validate_should_generateErrorWhenFromCodeIsNull() {
		this.currency.setToCode("PLN");
		this.currency.setRate(4.45);

		this.validator.validate(currency, errors);
		assertTrue(errors.hasErrors());
		assertNotNull(errors.getFieldError("fromCode"));
	}

	@Test
	void validate_should_generateErrorWhenFromCodeIsEmpty() {
		this.currency.setFromCode("   ");
		this.currency.setToCode("PLN");
		this.currency.setRate(4.45);

		this.validator.validate(currency, errors);
		assertTrue(errors.hasErrors());
		assertNotNull(errors.getFieldError("fromCode"));
	}

	@Test
	void validate_should_generateErrorWhenFromCodeLengthIsLongerThan3() {
		this.currency.setFromCode("EURR");
		this.currency.setToCode("PLN");
		this.currency.setRate(4.45);

		this.validator.validate(currency, errors);
		assertTrue(errors.hasErrors());
		assertNotNull(errors.getFieldError("fromCode"));
	}

	@Test
	void validate_should_generateErrorWhenFromCodeLengthIsShorterThan3() {
		this.currency.setFromCode("EU");
		this.currency.setToCode("PLN");
		this.currency.setRate(0.22);

		this.validator.validate(currency, errors);
		assertTrue(errors.hasErrors());
		assertNotNull(errors.getFieldError("fromCode"));
	}

	@Test
	void validate_should_generateErrorWhenToCodeIsNull() {
		this.currency.setFromCode("EUR");
		this.currency.setRate(0.22);

		this.validator.validate(currency, errors);
		assertTrue(errors.hasErrors());
		assertNotNull(errors.getFieldError("toCode"));
	}

	@Test
	void validate_should_generateErrorWhenToCodeIsEmpty() {
		this.currency.setFromCode("EUR");
		this.currency.setToCode("     ");
		this.currency.setRate(0.22);

		this.validator.validate(currency, errors);
		assertTrue(errors.hasErrors());
		assertNotNull(errors.getFieldError("toCode"));
	}

	@Test
	void validate_should_generateErrorWhenToCodeLengthIsLongerThan3() {
		this.currency.setFromCode("EUR");
		this.currency.setToCode("PLNN");
		this.currency.setRate(0.22);

		this.validator.validate(currency, errors);
		assertTrue(errors.hasErrors());
		assertNotNull(errors.getFieldError("toCode"));
	}

	@Test
	void validate_should_generateErrorWhenToCodeLengthIsShorterThan3() {
		this.currency.setFromCode("EU");
		this.currency.setToCode("PL");
		this.currency.setRate(0.22);

		this.validator.validate(currency, errors);
		assertTrue(errors.hasErrors());
		assertNotNull(errors.getFieldError("toCode"));
	}

	@Test
	void validate_should_generateErrorWhenRateIsNull() {
		this.currency.setFromCode("EU");
		this.currency.setToCode("PL");

		this.validator.validate(currency, errors);
		assertTrue(errors.hasErrors());
		assertNotNull(errors.getFieldError("rate"));
	}

	@Test
	void validate_should_generateErrorWhenRateIsEqualTo0() {
		this.currency.setFromCode("EU");
		this.currency.setToCode("PL");
		this.currency.setRate(0.0);

		this.validator.validate(currency, errors);
		assertTrue(errors.hasErrors());
		assertNotNull(errors.getFieldError("rate"));
	}

	@Test
	void validate_should_generateErrorWhenRateIsLessThan0() {
		this.currency.setFromCode("EU");
		this.currency.setToCode("PL");
		this.currency.setRate(-0.1);

		this.validator.validate(currency, errors);
		assertTrue(errors.hasErrors());
		assertNotNull(errors.getFieldError("rate"));
	}
}
