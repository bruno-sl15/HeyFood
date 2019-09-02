package com.heyfood.heyfoodapp.infra;

public class HeyFoodAppRuntimeException extends RuntimeException {
    public HeyFoodAppRuntimeException(String msg) {
        super(msg);
    }
    public HeyFoodAppRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
