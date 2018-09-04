package com.ldlywt.base.frame.http;

import com.ldlywt.base.utils.XHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;


/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/31
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class XHttp {

    public static XHandler handler = new XHandler();
    private static IHttpEngine sHttpEngine;

    private XHttp() {
        if (sHttpEngine == null) {
            throw new NullPointerException("Call XFrame.initXHttp(IHttpEngine httpEngine) within your Application onCreate() method." +
                    "Or extends XApplication");
        }
    }

    public static void init(IHttpEngine engine) {
        sHttpEngine = engine;
    }


    public static XHttp getDefault() {
        return Holder.INSTANCE;
    }

    /**
     * 获取HttpCall上的泛型
     */
    public static Class<?> analysisClassInfo(Object obj) {
        Type genType = obj.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }


    public void get(String url, Map<String, Object> params, HttpCallBack callBack) {
        sHttpEngine.get(url, params, callBack);
    }


    public void post(String url, Map<String, Object> params, HttpCallBack callBack) {
        sHttpEngine.get(url, params, callBack);
    }


    private static class Holder {
        private static final XHttp INSTANCE = new XHttp();
    }
}
