package com.ldlywt.androidadvancedemo.hook;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <pre>
 *     @author : wutao
 *     e-mail : 670831931@qq.com
 *     time   : 2020/02/28
 *     desc   : 动态代理只能hook接口
 *     version: 1.0
 * </pre>
 */
public class HookUtils {

    public static void hookInstrumentation() {
        try {
            Class<?> instrumentationClass = Class.forName("android.app.Instrumentation");
            Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{instrumentationClass}, new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            if (method.getName().equals("execStartActivity")) {
                                int index = 0;
                                Log.i("wutao", "Hook成功 : execStartActivity");
                                Log.i("wutao", "Hook成功 : args[0]:  " + args[0]);

                            }


                            return null;
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
