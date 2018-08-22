package com.ldlywt.easyhttp;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class Request {
    private Map<String, String> headers;//http包请求头
    private String method;//请求方法，post 或者 get方法
    private HttpUrl httpUrl;//http的url信息
    private RequestBody requestBody;//如果是post请求，还会有requestBody存参数信息

    private Request(Builder builder) {
        headers = builder.headers;
        method = builder.method;
        httpUrl = builder.httpUrl;
        requestBody = builder.requestBody;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getMethod() {
        return method == null ? "" : method;
    }

    public HttpUrl getHttpUrl() {
        return httpUrl;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public static final class Builder {
        private Map<String, String> headers = new HashMap<>();
        private String method;
        private HttpUrl httpUrl;
        private RequestBody requestBody;

        public Builder addHeader(String key, String value) {
            headers.put(key, value);
            return this;
        }

        public Builder removeHeader(String key) {
            headers.remove(key);
            return this;
        }

        public Builder post(RequestBody requestBody) {
            this.requestBody = requestBody;
            this.method = "POST";
            return this;
        }

        public Builder get() {
            this.method = "GET";
            return this;
        }

        public Builder url(String url) {
            try {
                this.httpUrl = new HttpUrl(url);
                return this;
            } catch (MalformedURLException e) {
                throw new IllegalStateException("Http Url Format Error!", e);
            }
        }

        public Request build() {

            if (null == httpUrl) {
                throw new IllegalStateException("url is null!");
            }
            if (null == method) {
                method = "GET";
            }

            return new Request(this);
        }
    }
}
