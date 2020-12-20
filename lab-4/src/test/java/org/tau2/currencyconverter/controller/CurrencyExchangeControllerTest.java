package org.tau2.currencyconverter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.tau2.currencyconverter.service.CurrencyService;
import org.tau2.currencyconverter.transferObject.CurrencyExchange;
import org.tau2.currencyconverter.transferObject.NewRate;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CurrencyExchangeControllerTest {

	ObjectMapper objectMapper = new ObjectMapper();
	MockMvc mockMvc;
	@Mock
	CurrencyService currencyService;
	@InjectMocks
	CurrencyExchangeController currencyExchangeController;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(currencyExchangeController).build();
	}

	@Test
	void updateRate_shouldReturnStatusOk_when_RateIsUpdated() throws Exception {
		when(currencyService.update(anyLong(), any(NewRate.class))).thenReturn(true);

		mockMvc.perform(patch("/currencies/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new NewRate())))
				.andExpect(status().isOk());
	}

	@Test
	void updateRate_shouldReturnStatusNotFound_when_RateIsUpdated() throws Exception {
		NewRate rate = new NewRate();
		when(currencyService.update(anyLong(), any(NewRate.class))).thenReturn(false);

		mockMvc.perform(patch("/currencies/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new NewRate())))
				.andExpect(status().isNotFound());
	}

	@Test
	void getAllCurrencies_shouldReturnNotEmptyList_when_currenciesExists() throws Exception {
		CurrencyExchange currencyExchange1 = new CurrencyExchange();
		CurrencyExchange currencyExchange2 = new CurrencyExchange();
		List<CurrencyExchange> currencies = List.of(currencyExchange1, currencyExchange2);
		when(this.currencyService.getAllCurrencies()).thenReturn(currencies);

		mockMvc.perform(get("/currencies"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	void getAllCurrencies_shouldReturnEmptyList_when_currenciesDoesNotExist() throws Exception {
		when(this.currencyService.getAllCurrencies()).thenReturn(new ArrayList<>());

		mockMvc.perform(get("/currencies"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}
}
