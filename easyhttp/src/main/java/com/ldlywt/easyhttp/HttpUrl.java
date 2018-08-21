package com.ldlywt.easyhttp;

import android.text.TextUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HttpUrl {

    private String protocol;//协议，http或者https
    private String host;//服务器地址
    private String file;//请求服务器文件路径
    private int port;//服务器服务端口

    public HttpUrl(String url) throws MalformedURLException {
        URL localUrl = new URL(url);
        host = localUrl.getHost();
        protocol = localUrl.getProtocol();
        file = localUrl.getFile();
        port = localUrl.getPort();
        if (port == -1) {
            //代表url中没有端口信息，就是使用默认端口，http:80,https:443
            port = localUrl.getDefaultPort();
        }

        if (TextUtils.isEmpty(file)) {
            //如果为空，默认加上"/"
            file = "/";
        }
    }

    public String getProtocol() {
        return protocol == null ? "" : protocol;
    }

    public String getHost() {
        return host == null ? "" : host;
    }

    public String getFile() {
        return file == null ? "" : file;
    }

    public int getPort() {
        return port;
    }
}
