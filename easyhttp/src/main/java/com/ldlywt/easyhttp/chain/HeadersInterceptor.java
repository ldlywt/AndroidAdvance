package com.ldlywt.easyhttp.chain;

import com.ldlywt.easyhttp.HttpCodec;
import com.ldlywt.easyhttp.Request;
import com.ldlywt.easyhttp.Response;

import java.io.IOException;
import java.util.Map;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/21
 *     desc   : Http头拦截器
 *     version: 1.0
 * </pre>
 */
public class HeadersInterceptor implements Interceptor {
    @Override
    public Response intercept(InterceptorChain interceptorChain) throws IOException {
        Request request = interceptorChain.call.getRequest();
        Map<String, String> headers = request.getHeaders();

        if (!headers.containsKey(HttpCodec.HEAD_HOST)) {
            headers.put(HttpCodec.HEAD_HOST, request.getHttpUrl().getHost());
        }

        if (!headers.containsKey(HttpCodec.HEAD_CONNECTION)) {
            headers.put(HttpCodec.HEAD_CONNECTION, HttpCodec.HEAD_VALUE_KEEP_ALIVE);
        }

        if (null != request.getRequestBody()) {
            String contentType = request.getRequestBody().getContentType();
            if (contentType != null) {
                headers.put(HttpCodec.HEAD_CONTENT_TYPE, contentType);
            }
            long contentLength = request.getRequestBody().getContentLength();

            if (-1 != contentLength) {
                headers.put(HttpCodec.HEAD_CONTENT_LENGTH, Long.toString(contentLength));
            }
        }
        return interceptorChain.proceed();
    }
}
