package com.ldlywt.easyhttp;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/21
 *     desc   : 网络请求任务调度器，通过线程池来控制
 *     version: 1.0
 * </pre>
 */
public class Dispatcher {

    /**
     * Ready async calls in the order they'll be run.
     * 待执行异步任务队列
     */
    private final Deque<Call.AsyncCall> readyAsyncCalls = new ArrayDeque<>();
    /**
     * Running asynchronous calls. Includes canceled calls that haven't finished yet.
     * 运行中异步任务队列
     */
    private final Deque<Call.AsyncCall> runningAsyncCalls = new ArrayDeque<>();
    /**
     * Running synchronous calls. Includes canceled calls that haven't finished yet.
     * 运行中同步任务队列
     */
    private final Deque<Call> runningSyncCalls = new ArrayDeque<>();
    /**
     * 最多同时请求的数量
     */
    private int maxRequests = 64;
    /**
     * 同一个host最多允许请求的数量
     */
    private int maxRequestsPerHost = 5;
    /**
     * Executes calls. Created lazily.
     * 线程池
     */
    private ExecutorService executorService;

    public Dispatcher(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public Dispatcher() {
    }

    /**
     * 如果满足条件：
     * 当前请求数小于最大请求数（64）
     * 对单一host的请求小于阈值（5）
     * 将该任务插入正在执行任务队列，并执行对应任务。如果不满足则将其放入待执行队列。
     */
    public void enqueue(Call.AsyncCall asynCall) {
        if (runningAsyncCalls.size() < maxRequests && runningCallsForHost(asynCall) < maxRequestsPerHost) {
            runningAsyncCalls.add(asynCall);
            executorService().execute(asynCall);
        } else {
            readyAsyncCalls.add(asynCall);
        }
    }

    /**
     * Returns the number of running calls that share a host with {@code call}.
     * 获取同一host在正在运行队列中的数量
     */
    private int runningCallsForHost(Call.AsyncCall call) {
        int result = 0;
        for (Call.AsyncCall c : runningAsyncCalls) {
            if (c.getHost().equals(call.getHost())) result++;
        }
        return result;
    }

    /**
     * 任务队列线程池
     */
    public synchronized ExecutorService executorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp Dispatcher", false));
        }
        return executorService;
    }

    public void executed(Call call) {
        runningSyncCalls.add(call);
    }

    public void finished(Call.AsyncCall asyncCall) {
        finished(runningAsyncCalls, asyncCall, true);
    }

    private <T> void finished(Deque<T> calls, T call, boolean promoteCalls) {
        synchronized (this) {
            if (!calls.remove(call)) {
                throw new AssertionError("Call wasn't in-flight!");
            }
            if (promoteCalls) {
                promoteCalls();
            }
        }

    }

    /**
     * 检查是否可以运行等待中的请求
     * 扫描待执行任务队列，将任务放入正在执行任务队列，并执行该任务。
     */
    private void promoteCalls() {
        if (runningAsyncCalls.size() >= maxRequests) return; // Already running max capacity.
        if (readyAsyncCalls.isEmpty()) return; // No ready calls to promote.

        for (Iterator<Call.AsyncCall> i = readyAsyncCalls.iterator(); i.hasNext(); ) {
            Call.AsyncCall call = i.next();

            if (runningCallsForHost(call) < maxRequestsPerHost) {
                i.remove();
                runningAsyncCalls.add(call);
                executorService().execute(call);
            }

            if (runningAsyncCalls.size() >= maxRequests) return; // Reached max capacity.
        }
    }

    public synchronized int runningCallsCount() {
        return runningAsyncCalls.size() + runningSyncCalls.size();
    }

    public void finished(Call call) {
        finished(runningSyncCalls, call, false);
    }


    public synchronized int getMaxRequests() {
        return maxRequests;
    }

    /**
     * Set the maximum number of requests to execute concurrently. Above this requests queue in
     * memory, waiting for the running calls to complete.
     * <p>
     * <p>If more than {@code maxRequests} requests are in flight when this is invoked, those requests
     * will remain in flight.
     */
    public synchronized void setMaxRequests(int maxRequests) {
        if (maxRequests < 1) {
            throw new IllegalArgumentException("max < 1: " + maxRequests);
        }
        this.maxRequests = maxRequests;
    }

    public synchronized int getMaxRequestsPerHost() {
        return maxRequestsPerHost;
    }

    /**
     * Set the maximum number of requests for each host to execute concurrently. This limits requests
     * by the URL's host name. Note that concurrent requests to a single IP address may still exceed
     * this limit: multiple hostnames may share an IP address or be routed through the same HTTP
     * proxy.
     * <p>
     * <p>If more than {@code maxRequestsPerHost} requests are in flight when this is invoked, those
     * requests will remain in flight.
     */
    public synchronized void setMaxRequestsPerHost(int maxRequestsPerHost) {
        if (maxRequestsPerHost < 1) {
            throw new IllegalArgumentException("max < 1: " + maxRequestsPerHost);
        }
        this.maxRequestsPerHost = maxRequestsPerHost;
    }
}
