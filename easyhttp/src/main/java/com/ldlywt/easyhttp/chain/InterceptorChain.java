package com.ldlywt.easyhttp.chain;

import com.ldlywt.easyhttp.Call;
import com.ldlywt.easyhttp.HttpConnection;
import com.ldlywt.easyhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class InterceptorChain implements Interceptor.Chain{

    final List<Interceptor> interceptors;
    final int index;
    final Call call;
    HttpConnection httpConnection;

    public InterceptorChain(List<Interceptor> interceptors, int index, Call call,HttpConnection httpConnection) {
        this.interceptors = interceptors;
        this.index = index;
        this.call = call;
        this.httpConnection = httpConnection;
    }

    public Response proceed(HttpConnection httpConnection) throws IOException{
        this.httpConnection = httpConnection;
        return proceed();
    }

    @Override
    public Response proceed() throws IOException {
        if (index > interceptors.size()) {
            throw new IOException("Interceptor Chain Error");
        }
        InterceptorChain next = new InterceptorChain(interceptors, index + 1, call, httpConnection);
        Interceptor interceptor = interceptors.get(index);
        Response response = interceptor.intercept(next);
        return response;
    }
}

