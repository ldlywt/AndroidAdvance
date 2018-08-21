package com.ldlywt.easyhttp;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ConnectionPool {

    /**
     * Background threads are used to cleanup expired connections. There will be at most a single
     * thread running per connection pool. The thread pool executor permits the pool itself to be
     * garbage collected.
     */
    private static final Executor executor = new ThreadPoolExecutor(0 /* corePoolSize */,
            Integer.MAX_VALUE /* maximumPoolSize */, 60L /* keepAliveTime */, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp ConnectionPool", true));
    private final Runnable cleanupRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                long now = System.currentTimeMillis();
                long waitDuration = cleanup(now);
                if (waitDuration == -1) {
                    return;//连接池为空，清理线程执行结束
                }

                if (waitDuration > 0) {
                    synchronized (ConnectionPool.this) {
                        try {
                            ConnectionPool.this.wait(waitDuration);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    };

    /**
     * 根据当前时间，清理无用的连接
     */
    private long cleanup(long now) {

        return 0;
    }

    private long keepAliveTime;
    private Deque<HttpConnection> httpConnections = new ArrayDeque<>();
    private boolean cleanupRunning;


    public ConnectionPool() {
        this(60L, TimeUnit.SECONDS);
    }


    public ConnectionPool(long keepAliveTime, TimeUnit timeUnit) {
        this.keepAliveTime = timeUnit.toMillis(keepAliveTime);
    }

    public void putHttpConnection(HttpConnection  httpConnection){
        if (!cleanupRunning) {
            cleanupRunning = true;
            executor.execute(cleanupRunnable);
        }
        httpConnections.add(httpConnection);
    }

    /**
     * 根据服务器地址与端口，来获取可用的连接
     */
    public synchronized HttpConnection getHttpConnection(String host, int port){
        Iterator<HttpConnection> iterator = httpConnections.iterator();
        while (iterator.hasNext()) {
            HttpConnection connection = iterator.next();
            if (connection.isSameAddress(host,port)) {
                httpConnections.remove(connection);
                return connection;
            }
        }
        return null;
    }


}
