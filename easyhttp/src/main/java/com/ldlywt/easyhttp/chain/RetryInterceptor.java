package com.ldlywt.easyhttp.chain;

import com.ldlywt.easyhttp.Call;
import com.ldlywt.easyhttp.Response;

import java.io.IOException;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/21
 *     desc   : 重连拦截器
 *     version: 1.0
 * </pre>
 */
public class RetryInterceptor implements Interceptor {

    @Override
    public Response intercept(InterceptorChain interceptorChain) throws IOException {
        Call call = interceptorChain.call;
        IOException ioException = null;
        for (int i = 0; i < call.getHttpClient().getRetryTimes(); i++) {
            if (call.isCanceled()) {
                throw new IOException("this task had canceled");
            }

            try {
                return interceptorChain.proceed();
            }catch (IOException e){
                ioException = e;
            }
        }
        throw ioException;
    }
}
