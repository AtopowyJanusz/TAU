package org.tau2.currencyconverter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CurrencyExchangeEntity {

	@Id
	@GeneratedValue
	private Long id;
	private String fromCode;
	private String toCode;
	private double convertRate;

	public CurrencyExchangeEntity() {
	}

	public CurrencyExchangeEntity(String from, String toCode, double convertRate) {
		this.fromCode = from;
		this.toCode = toCode;
		this.convertRate = convertRate;
	}

	public CurrencyExchangeEntity(Long id, String fromCode, String toCode, double convertRate) {
		this(fromCode, toCode, convertRate);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFromCode() {
		return fromCode;
	}

	public void setFromCode(String fromCode) {
		this.fromCode = fromCode;
	}

	public String getToCode() {
		return toCode;
	}

	public void setToCode(String toCode) {
		this.toCode = toCode;
	}

	public double getConvertRate() {
		return convertRate;
	}

	public void setConvertRate(double convertRate) {
		this.convertRate = convertRate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CurrencyExchangeEntity that = (CurrencyExchangeEntity) o;

		if (!fromCode.equals(that.fromCode)) return false;
		return toCode.equals(that.toCode);
	}

	@Override
	public int hashCode() {
		int result = fromCode.hashCode();
		result = 31 * result + toCode.hashCode();
		return result;
	}
}
