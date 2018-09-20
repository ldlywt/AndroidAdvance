package com.ldlywt.xthread;

import java.util.concurrent.ExecutorService;

/**
 * 线程调度器接口.
 * 优先级获取的策略:
 * {@link #registerPriority(Runnable, Priority)}  > {@link #registerPriority(Class, Priority)}
 */
public interface IDispatcher extends IPrioritize, IExecuteCallback {
    /**
     * 提交一个任务到线程池,设置为默认优先级
     */
    void post(Runnable r);

    /**
     * 提交一个UI相关业务runnable，此业务优先级别最高，请根据业务的紧急程度添加
     */
    void postHighPriority(Runnable r);

    /**
     * 提交一个普通优先级的后台Work
     */
    void postNormalPriority(Runnable r);

    /**
     * 提交一个低优先级的后台Work
     */
    void postLowPriority(Runnable r);

    /**
     * 提交一个指定优先级的任务,
     */
    void post(Runnable r, Priority priority);

    /**
     * {@link #postDelayed(Runnable, long, Priority)}
     */
    void postDelayed(Runnable r, long delay);

    /**
     * 延时提交业务runnable，runnable需设置好对应业务优先级，默认为Normal
     *
     * @param delay 单位:ms
     */
    void postDelayed(Runnable r, long delay, Priority priority);

    /**
     * {@link #postSerial(Runnable, Priority)}
     */
    void postSerial(Runnable r);

    /**
     * 顺序串行任务
     */
    void postSerial(Runnable r, Priority priority);

    /**
     * 从队列中移除相关业务runnable
     */
    void remove(Runnable r);

    /**
     * 在主线程中执行,如果当前是子线程,则执行{@link #postOnMain(Runnable)}
     */
    void runOnMain(Runnable r);

    /**
     * 提交到主线程Looper的消息队列中执行
     */
    void postOnMain(Runnable r);

    /**
     * 延时提交到主线程Looper的消息队列中执行
     *
     * @param delay 单位:ms
     */
    void postOnMainDelayed(Runnable r, long delay);

    /**
     * 移除一个主线任务
     */
    void removeOnMain(Runnable r);

    /**
     * 获取当前调度器的线程池
     */
    ExecutorService getExecutorService();
}
