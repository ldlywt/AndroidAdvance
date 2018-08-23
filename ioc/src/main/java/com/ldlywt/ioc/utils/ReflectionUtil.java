package com.ldlywt.ioc.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ReflectionUtil {

    public static Object invoke(Method method, Object obj, Object... args) throws Exception{
        boolean access = method.isAccessible();
        try {
            method.setAccessible(true);
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw new LighterReflectionException("Lighter method invocation has error.", e);
        } finally {
            method.setAccessible(access);
        }
    }

    public static void setValue(Field field, Object instance, Object value) throws LighterReflectionException{
        if (instance == null || value == null) {
            return;
        }
        boolean access = field.isAccessible();
        try {
            field.setAccessible(true);
            field.set(instance, value);
        } catch (Exception e) {
            throw new LighterReflectionException("Lighter set value has error.", e);
        } finally {
            field.setAccessible(access);
        }
    }
}
