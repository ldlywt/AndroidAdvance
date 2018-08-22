package com.ldlywt.easyhttp.chain;

import com.ldlywt.easyhttp.HttpCodec;
import com.ldlywt.easyhttp.HttpConnection;
import com.ldlywt.easyhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/21
 *     desc   : 通信拦截器
 *     version: 1.0
 * </pre>
 */
public class CallServiceInterceptor implements Interceptor {
    @Override
    public Response intercept(InterceptorChain interceptorChain) throws IOException {

        HttpConnection httpConnection = interceptorChain.httpConnection;
        HttpCodec httpCodec = new HttpCodec();
        InputStream inputStream = httpConnection.call(httpCodec);
        //获取服务器返回的响应行 HTTP/1.1 200 OK\r\n
        String statusLine = httpCodec.readLine(inputStream);

        //获取服务器返回的响应头
        Map<String, String> headers = httpCodec.readHeaders(inputStream);

        //根据Content-Length或者Transfer-Encoding(分块)计算响应体的长度
        int contentLength = -1;
        if (headers.containsKey(HttpCodec.HEAD_CONTENT_LENGTH)) {
            contentLength = Integer.valueOf(headers.get(HttpCodec.HEAD_CONTENT_LENGTH));
        }

        //是否为分块编码
        boolean isChunked = false;
        if (headers.containsKey(HttpCodec.HEAD_TRANSFER_ENCODING)) {
            isChunked = headers.get(HttpCodec.HEAD_TRANSFER_ENCODING).equalsIgnoreCase(HttpCodec.HEAD_VALUE_CHUNKED);
        }

        //获取服务器响应体
        String body = null;
        if (contentLength > 0) {
            byte[] bodyBytes = httpCodec.readBytes(inputStream, contentLength);
            body = new String(bodyBytes, HttpCodec.ENCODE);
        } else if (isChunked) {
            body = httpCodec.readChunked(inputStream, contentLength);
        }

        // HTTP/1.1 200 OK\r\n status[0] = "HTTP/1.1",status[1] = "200",status[2] = "OK\r\n"
        String[] status = statusLine.split(" ");

        //根据响应头中的Connection的值，来判断是否能够复用连接
        boolean isKeepAlive = false;
        if (headers.containsKey(HttpCodec.HEAD_CONNECTION)) {
            isKeepAlive = headers.get(HttpCodec.HEAD_CONNECTION).equalsIgnoreCase(HttpCodec.HEAD_VALUE_KEEP_ALIVE);
        }

        //更新此请求的最新使用时间，作用于线程池的清理工作
        httpConnection.updateLastUseTime();

        return new Response(Integer.valueOf(status[1]), contentLength, headers, body, isKeepAlive);
    }
}
