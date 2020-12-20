package org.tau2.currencyconverter.transferObject;

public class CurrencyExchange {

	private Long id;
	private String fromCode;
	private String toCode;
	private double convertRate;

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
}
