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
	void updateRate_shouldReturnStatusNotFound_when_RateDoesntExist() throws Exception {
		when(currencyService.update(anyLong(), any(NewRate.class))).thenReturn(false);

		mockMvc.perform(patch("/currencies/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new NewRate())))
				.andExpect(status().isNotFound());
	}

	@Test
	void getAllCurrencies_shouldReturnNotEmptyList_when_currenciesExists() throws Exception {
		CurrencyExchange currencyExchange1 = new CurrencyExchange();
		currencyExchange1.setId(1L);
		currencyExchange1.setConvertRate(4.5d);
		currencyExchange1.setFromCode("EUR");
		currencyExchange1.setToCode("PLN");
		CurrencyExchange currencyExchange2 = new CurrencyExchange();
		currencyExchange2.setId(2L);
		currencyExchange2.setConvertRate(0.22d);
		currencyExchange2.setFromCode("PLN");
		currencyExchange2.setToCode("EUR");
		List<CurrencyExchange> currencies = List.of(currencyExchange1, currencyExchange2);
		when(this.currencyService.getAllCurrencies()).thenReturn(currencies);

		mockMvc.perform(get("/currencies"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$.[0].id").value(1L))
				.andExpect(jsonPath("$.[0].convertRate").value(4.5d))
				.andExpect(jsonPath("$.[0].fromCode").value("EUR"))
				.andExpect(jsonPath("$.[0].toCode").value("PLN"))
				.andExpect(jsonPath("$.[1].id").value(2L))
				.andExpect(jsonPath("$.[1].convertRate").value(0.22d))
				.andExpect(jsonPath("$.[1].fromCode").value("PLN"))
				.andExpect(jsonPath("$.[1].toCode").value("EUR"));

	}

	@Test
	void getAllCurrencies_shouldReturnEmptyList_when_currenciesDoesNotExist() throws Exception {
		when(this.currencyService.getAllCurrencies()).thenReturn(new ArrayList<>());

		mockMvc.perform(get("/currencies"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	void getAllCurrencies_shouldReturnBadRequest_when_stringHasBeenProvidedInsteadOfIdInUri() throws Exception {
		mockMvc.perform(patch("/currencies/s")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new NewRate())))
				.andExpect(status().isBadRequest());
	}
}
