package com.heyfood.heyfoodapp.infra;

public class HeyFoodAppException extends Exception {
    public HeyFoodAppException(String msg) {
        super(msg);
    }
    public HeyFoodAppException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
