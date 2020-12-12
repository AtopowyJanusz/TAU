package org.tau2.currencyconverter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tau2.currencyconverter.service.CurrencyService;
import org.tau2.currencyconverter.transferObject.CurrencyExchange;

import java.util.List;

@RestController
@RequestMapping("/currencies")
public class CurrencyExchangeController {

	private final CurrencyService currencyService;

	public CurrencyExchangeController(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	@GetMapping
	public ResponseEntity<List<CurrencyExchange>> getAllCurrencies() {
		List<CurrencyExchange> currencies = this.currencyService.getAllCurrencies();
		return new ResponseEntity<>(currencies, HttpStatus.OK);
	}
}
