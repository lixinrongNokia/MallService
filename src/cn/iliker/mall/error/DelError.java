package cn.iliker.mall.error;

public class DelError extends Exception {
	public DelError() {
		super();
	}

	public DelError(String message) {
		super(message);
	}

	public DelError(String message, Throwable cause) {
		super(message, cause);
	}

	public DelError(Throwable cause) {
		super(cause);
	}

	protected DelError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
