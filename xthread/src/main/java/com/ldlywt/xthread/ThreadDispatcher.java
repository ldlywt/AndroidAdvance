package com.ldlywt.xthread;

/**
 * Created by Administrator on 2017/6/19 0019.
 * 线程分发,统一管理和维护并发线程
 */
public final class ThreadDispatcher {
    public static IDispatcher getDispatcher() {
        return DispatcherImpl.getInstance();
    }
}