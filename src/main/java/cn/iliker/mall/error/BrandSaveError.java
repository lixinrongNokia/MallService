package cn.iliker.mall.error;

public class BrandSaveError extends Exception{
    public BrandSaveError() {
        super();
    }

    public BrandSaveError(String message) {
        super(message);
    }

    public BrandSaveError(String message, Throwable cause) {
        super(message, cause);
    }

    public BrandSaveError(Throwable cause) {
        super(cause);
    }

    protected BrandSaveError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
