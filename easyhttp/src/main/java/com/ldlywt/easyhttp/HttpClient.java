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

    private Dispatcher mDispather;

    private List<Interceptor> mInterceptorList;

    private int mRetryTimes;

    private ConnectionPool mConnectionPool;

    private HttpClient(Builder builder) {
        mDispather = builder.mDispather;
        mInterceptorList = builder.mInterceptorList;
        mRetryTimes = builder.mRetryTimes;
        mConnectionPool = builder.mConnectionPool;
    }

    /**
     *  生成一个网络请求Call对象实例
     * Call call = client.newCall(request);
     */
    public Call newCall(Request request) {
        return new Call(this, request);
    }

    //--------------------------get方法----------------------

    public Dispatcher getDispather() {
        return mDispather;
    }

    public List<Interceptor> getInterceptorList() {
        if (mInterceptorList == null) {
            return new ArrayList<>();
        }
        return mInterceptorList;
    }

    public int getRetryTimes() {
        return mRetryTimes;
    }

    public ConnectionPool getConnectionPool() {
        return mConnectionPool;
    }

    //--------------------------builder--------------------

    public static final class Builder {
        private Dispatcher mDispather;
        private List<Interceptor> mInterceptorList;
        private int mRetryTimes;
        private ConnectionPool mConnectionPool;

        public Builder() {
        }

        public Builder mDispather(Dispatcher val) {
            mDispather = val;
            return this;
        }

        public Builder mInterceptorList(List<Interceptor> val) {
            mInterceptorList = val;
            return this;
        }

        public Builder mRetryTimes(int val) {
            mRetryTimes = val;
            return this;
        }

        public Builder mConnectionPool(ConnectionPool val) {
            mConnectionPool = val;
            return this;
        }

        public HttpClient build() {
            if (null == mDispather) {
                mDispather = new Dispatcher();
            }

            if (null == mConnectionPool) {
                mConnectionPool = new ConnectionPool();
            }
            return new HttpClient(this);
        }
    }
}
