package com.ldlywt.xthread;

/**
 * Created by LKF on 2018/9/17 0017.
 */
public interface OnExecuteListener {
    /**
     * 此回调是在执行{@link java.util.concurrent.ExecutorService#execute(Runnable)}之前的回调.
     * 也就是说,执行完该回调以后任务不一定会立即执行,有可能会被加到队列中.
     * 可以在此时去为Runnable注册优先级{@link IDispatcher#registerPriority(Runnable, Priority)},或者其他准备工作
     */
    void onPreExecute(IDispatcher dispatcher, Runnable r);

    /**
     * {@link java.util.concurrent.ThreadPoolExecutor#beforeExecute(Thread, Runnable)}
     */
    void beforeExecute(IDispatcher dispatcher, Thread t, Runnable r);

    /**
     * {@link java.util.concurrent.ThreadPoolExecutor#afterExecute(Runnable, Throwable)}
     */
    void afterExecute(IDispatcher dispatcher, Runnable r, Throwable t);
}