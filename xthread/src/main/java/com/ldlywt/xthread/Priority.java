package com.ldlywt.xthread;

/**
 * Created by Administrator on 2017/6/19 0019.
 * 业务优先级
 */
public enum Priority {
    /**
     * 高优先级,线程调度会预留单独的线程给该优先级的业务,一般是用于UI渲染依赖的业务,比如:网络请求-数据解析-提交到UI线程渲染
     */
    HIGH(Integer.MIN_VALUE),

    NORMAL(HIGH.value() + 1),

    LOW(NORMAL.value() + 1);

    private int priority;

    Priority(int priority) {
        this.priority = priority;
    }

    public int value() {
        return priority;
    }
}
