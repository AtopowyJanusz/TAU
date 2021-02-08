package org.tau2.currencyconverter.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.tau2.currencyconverter.transferObject.NewCurrencyExchange;

public class NewCurrencyExchangeValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return NewCurrencyExchange.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		NewCurrencyExchange currency = (NewCurrencyExchange) o;

		validateCode(currency.getFromCode(), "fromCode", errors);
		validateCode(currency.getToCode(), "toCode", errors);

		if (currency.getRate() == null) {
			errors.rejectValue("rate", "rate can not be empty");
		} else if (currency.getRate() <= 0.0) {
			errors.rejectValue("rate", "rate can not be less than 0.0");
		}
	}

	private void validateCode(String code, String fieldName, Errors errors) {
		if (StringUtils.isBlank(code)) {
			errors.rejectValue(fieldName, fieldName + " can not be empty");
		} else if (code.length() != 3) {
			errors.rejectValue(fieldName, fieldName + " has to be 3 characters long");
		}
	}
}
