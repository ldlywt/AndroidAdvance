package com.ldlywt.easyhttp;

import android.util.Log;

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
    private long keepAliveTime;
    private Deque<HttpConnection> httpConnections = new ArrayDeque<>();
    private boolean cleanupRunning;
    /**
     * 生成一个清理线程,这个线程会定期去检查，并且清理那些无用的连接，这里的无用是指没使用的间期超过了保留时间
     * 其触发时机有两个：
     * 1.当连接池中put新的连接时
     * 2.当connectionBecameIdle接口被调用时
     * 这段死循环实际上是一个阻塞的清理任务，首先进行清理(clean)，并返回下次需要清理的间隔时间，然后
     * 调用wait(timeout)进行等待以释放锁与时间片，当等待时间到了后，再次进行清理，并返回下次要清理的
     * 间隔时间...
     */
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

    public ConnectionPool() {
        this(60L, TimeUnit.SECONDS);
    }


    public ConnectionPool(long keepAliveTime, TimeUnit timeUnit) {
        this.keepAliveTime = timeUnit.toMillis(keepAliveTime);
    }

    /**
     * 根据当前时间，清理无用的连接
     */
    private long cleanup(long now) {
        //最长闲置的时间
        long longestIdleDuration = -1;
        synchronized (this) {
            Iterator<HttpConnection> iterator = httpConnections.iterator();
            while (iterator.hasNext()) {
                HttpConnection httpConnection = iterator.next();
                //计算闲置时间
                long idleDuration = now - httpConnection.lastUseTime;

                //根据闲置时间来判断是否需要被清理
                if (idleDuration > keepAliveTime) {
                    iterator.remove();
                    httpConnection.close();
                    Log.i("ConnectionPool", "超过闲置时间,移出连接池");
                    continue;
                }

                //然后就整个连接池中最大的闲置时间
                if (idleDuration > longestIdleDuration) {
                    longestIdleDuration = idleDuration;
                }
            }

            if (longestIdleDuration >= 0) {
                //这里返回的值，可以让清理线程知道，下一次清理要多久以后
                return keepAliveTime - longestIdleDuration;
            }

            //如果运行到这里的话，代表longestIdleDuration = -1，连接池中为空
            cleanupRunning = false;
            return longestIdleDuration;
        }
    }

    public void putHttpConnection(HttpConnection httpConnection) {
        if (!cleanupRunning) {
            cleanupRunning = true;
            executor.execute(cleanupRunnable);
        }
        httpConnections.add(httpConnection);
    }

    /**
     * 根据服务器地址与端口，来获取可用的连接
     */
    public synchronized HttpConnection getHttpConnection(String host, int port) {
        Iterator<HttpConnection> iterator = httpConnections.iterator();
        while (iterator.hasNext()) {
            HttpConnection connection = iterator.next();
            if (connection.isSameAddress(host, port)) {
                httpConnections.remove(connection);
                return connection;
            }
        }
        return null;
    }


}
