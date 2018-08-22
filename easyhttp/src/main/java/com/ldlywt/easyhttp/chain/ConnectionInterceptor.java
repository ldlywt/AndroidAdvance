package com.ldlywt.easyhttp.chain;

import android.util.Log;

import com.ldlywt.easyhttp.HttpClient;
import com.ldlywt.easyhttp.HttpConnection;
import com.ldlywt.easyhttp.HttpUrl;
import com.ldlywt.easyhttp.Request;
import com.ldlywt.easyhttp.Response;

import java.io.IOException;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/21
 *     desc   : 负责和服务器建立连接
 *     version: 1.0
 * </pre>
 */
public class ConnectionInterceptor implements Interceptor {
    @Override
    public Response intercept(InterceptorChain interceptorChain) throws IOException {
        Request request = interceptorChain.call.getRequest();
        HttpClient httpClient = interceptorChain.call.getHttpClient();
        HttpUrl httpUrl = request.getHttpUrl();

        HttpConnection httpConnection = httpClient.getConnectionPool().getHttpConnection(httpUrl.getHost(), httpUrl.getPort());
        if (httpConnection == null) {
            httpConnection = new HttpConnection();
        } else {
            Log.e("interceptor", "从连接池中获得连接");
        }
        httpConnection.setRequest(request);

        try {
            Response response = interceptorChain.proceed(httpConnection);
            if (response.isKeepAlive()){
                httpClient.getConnectionPool().putHttpConnection(httpConnection);
            }else{
                httpConnection.close();
            }
            return response;
        }catch (IOException e){
            httpConnection.close();
            throw e;
        }
    }
}
