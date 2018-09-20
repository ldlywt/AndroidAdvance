package com.ldlywt.xthread;

import android.os.Process;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class BizExecutor extends AbstractExecutorService implements ThreadFactory, IPrioritize, IExecuteCallback {
    private static final String TAG = "BizExecutor";
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int HIGH_PRIORITY_POOL_SIZE = Math.max(1, Math.min(CPU_COUNT / 2, 4));//高优先级并发数(1 < nCpu/2 < 4)
    private static final int OTHER_PRIORITY_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 7));//其他优先级并发数(2 < nCpu-1 < 7)
    private static final int KEEP_ALIVE_SECONDS = 20;
    private static final Priority DEFAULT_PRIORITY = Priority.NORMAL;

    private final ThreadPoolExecutor mHighPriorityExecutor;
    private final ThreadPoolExecutor mOtherPriorityExecutor;

    private final Map<Runnable, Priority> mTaskPriorityMap = new WeakHashMap<>();
    private final Map<Class<? extends Runnable>, Priority> mClassPriorityMap = new WeakHashMap<>();
    private Set<OnExecuteListener> mOnExecuteListeners = new HashSet<>();

    private final IDispatcher mDispatcher;

    BizExecutor(IDispatcher dispatcher) {
        mDispatcher = dispatcher;
        //高优先级线程池+队列
        final ThreadPoolExecutor highExecutor = new InnerExecutor(
                HIGH_PRIORITY_POOL_SIZE, HIGH_PRIORITY_POOL_SIZE,
                KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(), this);
        highExecutor.allowCoreThreadTimeOut(true);
        mHighPriorityExecutor = highExecutor;
        //其他优先级队列+线程池
        final BlockingQueue<Runnable> otherQueue = new PriorityBlockingQueue<>(11, new Comparator<Runnable>() {
            @Override
            public int compare(Runnable o1, Runnable o2) {
                final Priority leftPriority = getRunnablePriority(o1);
                final Priority rightPriority = getRunnablePriority(o2);
                return leftPriority.value() - rightPriority.value();
            }
        });
        final ThreadPoolExecutor otherExecutor = new InnerExecutor(
                OTHER_PRIORITY_POOL_SIZE, OTHER_PRIORITY_POOL_SIZE,
                KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                otherQueue, this);
        otherExecutor.allowCoreThreadTimeOut(true);
        mOtherPriorityExecutor = otherExecutor;
    }

    private void doPreExecute(Runnable r) {
        final Set<OnExecuteListener> listeners = new HashSet<>(mOnExecuteListeners);
        for (OnExecuteListener l : listeners) {
            l.onPreExecute(mDispatcher, r);
        }
    }

    /**
     * 获取高优先级任务队列
     */
    BlockingQueue<Runnable> getHighPriorityQueue() {
        return mHighPriorityExecutor.getQueue();
    }

    /**
     * 获取非高优先级任务队列
     */
    BlockingQueue<Runnable> getOtherPriorityQueue() {
        return mOtherPriorityExecutor.getQueue();
    }

    @Override
    public void shutdown() {
        //不允许shutdown
    }

    @NonNull
    @Override
    public List<Runnable> shutdownNow() {
        //不允许shutdown
        return new ArrayList<>();
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, @NonNull TimeUnit unit) {
        return false;
    }

    @Override
    public void execute(@NonNull Runnable command) {
        doPreExecute(command);
        final Priority priority = getRunnablePriority(command);
        if (Priority.HIGH == priority) {
            final int highSize = mHighPriorityExecutor.getQueue().size();
            final int otherSize = mOtherPriorityExecutor.getQueue().size();
            if (highSize * OTHER_PRIORITY_POOL_SIZE <= otherSize * HIGH_PRIORITY_POOL_SIZE) {
                mHighPriorityExecutor.execute(command);
            } else {
                mOtherPriorityExecutor.execute(command);
            }
        } else {
            mOtherPriorityExecutor.execute(command);
        }
    }

    @Override
    public Thread newThread(@NonNull Runnable r) {
        return new Thread(r) {
            @Override
            public void run() {
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                super.run();
                if (Process.THREAD_PRIORITY_BACKGROUND != Process.getThreadPriority(Process.myTid())) {
                }
            }
        };
    }

    @NonNull
    @Override
    public Priority getRunnablePriority(Runnable r) {
        Priority priority;
        if ((priority = mTaskPriorityMap.get(r)) != null)
            return priority;
        if ((priority = mClassPriorityMap.get(r.getClass())) != null)
            return priority;
        return DEFAULT_PRIORITY;
    }

    @Override
    public void registerPriority(Runnable r, Priority priority) {
        mTaskPriorityMap.put(r, priority);
    }

    @Override
    public void unregisterPriority(Runnable r) {
        mTaskPriorityMap.remove(r);
    }

    @Override
    public void registerPriority(Class<? extends Runnable> clazz, Priority priority) {
        mClassPriorityMap.put(clazz, priority);
    }

    @Override
    public void unregisterPriority(Class<? extends Runnable> clazz) {
        mClassPriorityMap.remove(clazz);
    }

    @Override
    public void addOnExecuteListener(OnExecuteListener listener) {
        if (listener == null)
            return;
        mOnExecuteListeners.add(listener);
    }

    @Override
    public void removeOnExecuteListener(OnExecuteListener listener) {
        if (listener == null)
            return;
        mOnExecuteListeners.remove(listener);
    }

    private class InnerExecutor extends ThreadPoolExecutor {
        InnerExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        private void doBeforeExecute(Thread t, Runnable r) {
            final Set<OnExecuteListener> listeners = new HashSet<>(mOnExecuteListeners);
            for (OnExecuteListener l : listeners) {
                l.beforeExecute(mDispatcher, t, r);
            }
        }

        private void doAfterExecute(Runnable r, Throwable t) {
            final Set<OnExecuteListener> listeners = new HashSet<>(mOnExecuteListeners);
            for (OnExecuteListener l : listeners) {
                l.afterExecute(mDispatcher, r, t);
            }
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            super.beforeExecute(t, r);
            doBeforeExecute(t, r);
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            doAfterExecute(r, t);
        }
    }
}