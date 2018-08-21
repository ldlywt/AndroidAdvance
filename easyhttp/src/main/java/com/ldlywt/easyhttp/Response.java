package com.ldlywt.easyhttp;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/21
 *     desc   : Http返回的实体类
 *     version: 1.0
 * </pre>
 */
public class Response {

    int code;
    int contentLength = -1; //返回包的长度
    Map<String,String> header = new HashMap<>(); // 返回包的头信息
    String body;
    boolean isKeepAlive;    //是否保持连接

    public Response(int code, int contentLength, Map<String, String> header, String body, boolean isKeepAlive) {
        this.code = code;
        this.contentLength = contentLength;
        this.header = header;
        this.body = body;
        this.isKeepAlive = isKeepAlive;
    }

    public int getCode() {
        return code;
    }

    public int getContentLength() {
        return contentLength;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public String getBody() {
        return body == null ? "" : body;
    }

    public boolean isKeepAlive() {
        return isKeepAlive;
    }
}
