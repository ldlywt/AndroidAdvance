package com.ldlywt.easyhttp.chain;

import com.ldlywt.easyhttp.Response;

import java.io.IOException;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface Interceptor {
    Response intercept(InterceptorChain interceptorChain) throws IOException;

    interface Chain{
        Response proceed() throws IOException;
    }
}
