package com.ldlywt.base.frame.http;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/31
 *     desc   : 如果是接口的话，在XHttp.analysisClassInfo(callBack)会报错
 *     version: 1.0
 * </pre>
 */
public abstract class HttpCallBack<T> {

    public abstract void onSuccess(T result);

    public abstract void onFailed(String error);

}
