package com.ldlywt.easyhttp;


import com.ldlywt.easyhttp.chain.Interceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/21
 *     desc   : 来源于 https://github.com/ouyangshengduo/SenduoHttp
 *     version: 1.0
 * </pre>
 */
public class HttpClient {

    private Dispatcher dispatcher;

    private List<Interceptor> interceptorList;

    private int retryTimes;

    private ConnectionPool connectionPool;

    public HttpClient(){
        this(new Builder());
    }

    private HttpClient(Builder builder) {
        dispatcher = builder.dispatcher;
        interceptorList = builder.interceptorList;
        retryTimes = builder.retryTimes;
        connectionPool = builder.connectionPool;
    }


    /**
     * 生成一个网络请求Call对象实例
     * Call call = client.newCall(request);
     */
    public Call newCall(Request request) {
        return new Call(this, request);
    }

    //--------------------------get方法----------------------

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public List<Interceptor> getInterceptorList() {
        if (interceptorList == null) {
            return new ArrayList<>();
        }
        return interceptorList;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public static final class Builder {
        private Dispatcher dispatcher;
        private List<Interceptor> interceptorList;
        private int retryTimes;
        private ConnectionPool connectionPool;

        public Builder() {
            dispatcher = new Dispatcher();
            connectionPool = new ConnectionPool();
            retryTimes = 3;
        }

        public Builder setDispatcher(Dispatcher val) {
            dispatcher = val;
            return this;
        }

        public Builder setInterceptorList(List<Interceptor> val) {
            interceptorList = val;
            return this;
        }

        public Builder setRetryTimes(int val) {
            retryTimes = val;
            return this;
        }

        public Builder setConnectionPool(ConnectionPool val) {
            connectionPool = val;
            return this;
        }

        public HttpClient build() {

            if (null == dispatcher) {
                dispatcher = new Dispatcher();
            }

            if (null == connectionPool) {
                connectionPool = new ConnectionPool();
            }

            if (0 == retryTimes) {
                retryTimes = 3;
            }
            return new HttpClient(this);
        }
    }

    //--------------------------builder--------------------


}
