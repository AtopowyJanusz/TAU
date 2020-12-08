package currencyconverter.transferObject;

public class CurrencyExchangeRequest {

	private String fromCode;
	private String toCode;
	private Integer amount;

	public CurrencyExchangeRequest(String fromCode, String toCode, Integer amount) {
		this.fromCode = fromCode;
		this.toCode = toCode;
		this.amount = amount;
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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
