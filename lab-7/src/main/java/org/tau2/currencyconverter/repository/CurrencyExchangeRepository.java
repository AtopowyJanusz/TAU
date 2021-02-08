package org.tau2.currencyconverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tau2.currencyconverter.model.CurrencyExchangeEntity;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchangeEntity, Long> {
	CurrencyExchangeEntity findByFromCodeAndToCode(String fromCode, String toCode);

	Boolean existsByFromCodeAndToCode(String fromCode, String toCode);
}
