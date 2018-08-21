package com.ldlywt.easyhttp.chain;

import com.ldlywt.easyhttp.Call;
import com.ldlywt.easyhttp.Response;

import java.util.ArrayList;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class InterceptorChain {
    public InterceptorChain(ArrayList<Interceptor> interceptors, int index, Call call, Object o) {
    }

    public Response proceed() {
        return null;
    }
}

