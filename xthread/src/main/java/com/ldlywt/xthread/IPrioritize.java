package com.ldlywt.xthread;

interface IPrioritize {
    /**
     * 为某个Runnable注册优先级.
     * 注意:优先级获取的顺序参考{@link IDispatcher}
     */
    void registerPriority(Runnable r, Priority priority);

    /**
     * 注销某个Runnable的优先级
     */
    void unregisterPriority(Runnable r);

    /**
     * 为某一个类型的Runnable注册优先级
     */
    void registerPriority(Class<? extends Runnable> clazz, Priority priority);

    /**
     * 注销某个类型Runnable的优先级
     */
    void unregisterPriority(Class<? extends Runnable> clazz);

    /**
     * 获取某个任务的优先级
     */
    Priority getRunnablePriority(Runnable r);
}
