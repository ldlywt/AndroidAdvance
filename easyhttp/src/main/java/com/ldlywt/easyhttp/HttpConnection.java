package com.ldlywt.easyhttp;

import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HttpConnection {

    Socket socket;
    long lastUseTime;
    private Request request;
    private InputStream inputStream;
    private OutputStream outputStream;

    public boolean isSameAddress(String host, int port) {
        if (null == socket) {
            return false;
        }
        return TextUtils.equals(request.getHttpUrl().getHost(), host) &&
                request.getHttpUrl().getPort() == port;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void updateLastUseTime() {
        lastUseTime = System.currentTimeMillis();
    }

    public void createSocket() throws IOException {
        if (null == socket || socket.isClosed()) {
            HttpUrl httpUrl = request.getHttpUrl();
            if (httpUrl.getProtocol().equalsIgnoreCase(HttpCodec.PROTOCOL_HTTPS)) {
                //如果是https，就需要使用jdk默认的SSLSocketFactory来创建socket
                socket = SSLSocketFactory.getDefault().createSocket();
            }else{
                socket = new Socket();
            }
            socket.connect(new InetSocketAddress(httpUrl.getHost(), httpUrl.getPort()));
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        }
    }

    /**
     * 关闭socket的连接
     */
    public void close(){
        if(null != socket){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public InputStream call(HttpCodec httpCodec) throws IOException {
        //创建socket
        createSocket();
        //发送请求
        httpCodec.writeRequest(outputStream,request);
        //返回服务器响应 (InputStream)
        return inputStream;
    }

}
