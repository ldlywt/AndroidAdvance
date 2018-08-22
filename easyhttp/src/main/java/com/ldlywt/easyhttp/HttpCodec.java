package com.ldlywt.easyhttp;

import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;


public class HttpCodec {

    static final String CRLF = "\r\n";
    static final int CR = 13;//回车的ASCII码
    static final int LF = 10;//换行的ASCII码
    static final String SPACE = " ";//一个空格
    static final String HTTP_VERSION = "HTTP/1.1";//http的版本信息
    static final String COLON = ":";//冒号

    public static final String HEAD_HOST = "Host";
    public static final String HEAD_CONNECTION = "Connection";
    public static final String HEAD_CONTENT_TYPE = "Content-Type";
    public static final String HEAD_CONTENT_LENGTH = "Content-Length";
    public static final String HEAD_TRANSFER_ENCODING = "Transfer-Encoding";

    public static final String HEAD_VALUE_KEEP_ALIVE = "Keep-Alive";
    public static final String HEAD_VALUE_CHUNKED = "chunked";

    public static final String PROTOCOL_HTTPS = "https";
    public static final String PROTOCOL_HTTP = "https";

    public static final String ENCODE = "UTF-8";

    private final ByteBuffer byteBuffer;

    public HttpCodec(){
        byteBuffer = ByteBuffer.allocate(10 * 1024);
    }

    /**
     * 拼接request数据流,写入到socket通道
     * @param os
     * @param request
     * @throws IOException
     */
    public void writeRequest(OutputStream os,Request request) throws IOException{

        StringBuffer sb = new StringBuffer();
        //GET /v3/weather/weatherInfo?key=064a7778b8389441e30f91b8a60c9b23&city=%25E6%25B7%25B1%25E5%259C%25B3 HTTP/1.1
        sb.append(request.getMethod());// GET
        sb.append(SPACE);//一个空格
        sb.append(request.getHttpUrl().getFile());// "/v3/weather/weatherInfo?key=064a7778b8389441e30f91b8a60c9b23&city=%25E6%25B7%25B1%25E5%259C%25B3"
        sb.append(SPACE);//一个空格
        sb.append(HTTP_VERSION);// HTTP/1.1
        sb.append(CRLF);//一个回车换行

        //TODO 拼接请求头

        Map<String,String> headers = request.getHeaders();
        for(Map.Entry<String,String> entry : headers.entrySet()){
            //Content-Type:	application/json;charset=UTF-8

            sb.append(entry.getKey());//map中的key值,例如：Content-Type
            sb.append(COLON);//一个冒号
            sb.append(SPACE);//一个空格
            sb.append(entry.getValue());//map中value值，例如：application/json;charset=UTF-8
            sb.append(CRLF);//最后面跟一个回车和换行
        }

        sb.append(CRLF);//请求头最后，还需要跟一格回车和换行

        //TODO 拼接请求体
        RequestBody requestBody = request.getRequestBody();
        if(null != requestBody){
            sb.append(requestBody.getBody());
        }

        os.write(sb.toString().getBytes());
        os.flush();
    }


    /**
     * 读取服务器返回回来的一行数据
     * @param is
     * @return
     * @throws IOException
     */
    public String readLine(InputStream is) throws IOException{

        //先把byteBuffer清理一下
        byteBuffer.clear();
        //然后标记一下
        byteBuffer.mark();

        boolean isMaybeEofLine = false;//可能为行结束的标志，当出现一个/r的时候，置为true，如果下一个是/n，就确定是行结束了
        byte b;
        while((b = (byte)is.read()) != -1){
            byteBuffer.put(b);
            if(b == CR){//如果读到一个 /r
                isMaybeEofLine = true;
            }else if(isMaybeEofLine){
                if(b == LF){//如果读到一个 /n了，意味着，行结束了
                    byte[] lineBytes = new byte[byteBuffer.position()];//new一个一行数据大小的字节数据
                    //然后重置byteBuffer
                    byteBuffer.reset();//与mark搭配使用，告诉ByteBuffer,使用者将要拿出从mark到当前保存的字节数据
                    byteBuffer.get(lineBytes);

                    byteBuffer.clear();//清空
                    byteBuffer.mark();
                    return new String(lineBytes,ENCODE);
                }
                //如果下一个字节不是 /n，把标志重新置为false
                isMaybeEofLine = false;
            }
        }
        throw new IOException("Response read line error");
    }


    /**
     * 读取服务器返回的响应头
     * @param is
     * @return
     * @throws IOException
     */
    public Map<String, String> readHeaders(InputStream is) throws IOException{

        HashMap<String,String> headers = new HashMap<>();
        while(true){
            String line = readLine(is);

            if(isEmptyLine(line)){
                //如果读到空行 \r\n 响应头读完了
                break;
            }

            int index = line.indexOf(":");//因为服务器返回的响应头中的格式也是key: value的格式
            if(index > 0){
                String key = line.substring(0,index);
                //这里加2 是因为，value前面还有冒号和空格，所以，value的第一个位置，需要往后移

                //减2是因为line后面有/r/n两个字节
                String value = line.substring(index + 2,line.length() - 2);

                headers.put(key,value);
            }

        }
        return headers;
    }

    /**
     * 根据长度读取字节数据
     * @param is
     * @param length
     * @return
     * @throws IOException
     */
    public byte[] readBytes(InputStream is,int length) throws IOException{
        byte[] bytes = new byte[length];
        int readNum = 0;
        while(true){
            readNum = is.read(bytes,readNum,length - readNum);
            if(readNum == length){
                return bytes;
            }
        }
    }

    /**
     * 服务器传输响应体的方式为分块方式，根据分块的方式获取响应体
     * @param is
     * @return
     * @throws IOException
     */
    public String readChunked(InputStream is,int length) throws IOException{
        int len = -1;
        boolean isEmptyData = false;
        StringBuffer chunked = new StringBuffer();
        while(true){
            if(len < 0){
                //获取块的长度
                String line = readLine(is);
                length += line.length();
                //去掉/r/n
                line = line.substring(0,line.length() - 2);
                //获得长度 16进制字符串转成10进制整型
                len = Integer.valueOf(line,16);

                //如果读到的是0，则再读一个/r/n就结束了
                isEmptyData = len == 0;
            }else{
                length += (len + 2);
                byte[] bytes = readBytes(is,len + 2);//读的时候，加上2，/r/n
                chunked.append(new String(bytes,ENCODE));
                len = -1;
                if(isEmptyData){
                    return chunked.toString();
                }
            }
        }
    }

    /**
     * 判断是否为空行，如果读到的是/r/n，就意味是空行
     * @param line
     * @return
     */
    private boolean isEmptyLine(String line){
        return TextUtils.equals(line,CRLF);
    }




}
