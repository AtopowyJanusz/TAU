package currencyconverter.exception;

public class NoSuchCurrencyExchangeException extends RuntimeException {

	public NoSuchCurrencyExchangeException(String message) {
		super(message);
	}
}
