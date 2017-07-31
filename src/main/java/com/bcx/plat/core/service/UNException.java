package com.bcx.plat.core.service;

/**
 * Created by Went on 2017/7/30.
 */
public class UNException extends RuntimeException {
    public UNException() {
        super();
    }

    public UNException(String message) {
        super(message);
    }

    public UNException(String message, Throwable cause) {
        super(message, cause);
    }

    public UNException(Throwable cause) {
        super(cause);
    }

    protected UNException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
