package com.ldlywt.ioc.utils;

/**
 * Description:反射异常
 */
public class LighterReflectionException extends Exception {
    public LighterReflectionException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    private static final long serialVersionUID = -2195818285577841800L;

}
