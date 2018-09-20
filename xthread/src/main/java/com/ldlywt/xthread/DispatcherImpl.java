package com.ldlywt.xthread;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;


/**
 * 线程分发管理的实现类
 */
class DispatcherImpl implements IDispatcher {
    private static final String TAG = "DispatcherImpl";

    private static DispatcherImpl sInstance;

    public static DispatcherImpl getInstance() {
        if (sInstance == null) {
            synchronized (DispatcherImpl.class) {
                if (sInstance == null) {
                    sInstance = new DispatcherImpl();
                }
            }
        }
        return sInstance;
    }

    private final Map<Runnable, Runnable> mDelayRunnableMap = new WeakHashMap<>();
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());
    private final SerialExecutor mSerialExecutor = new SerialExecutor();
    private final BizExecutor mExecutorService;

    private DispatcherImpl() {
        mExecutorService = new BizExecutor(this);
        final BizExecutor executor = mExecutorService;
        final ExecutorService oldExecutor = (ExecutorService) hookAsyncTaskField("THREAD_POOL_EXECUTOR", executor);
        if (oldExecutor != null && !oldExecutor.isShutdown()) {
            oldExecutor.shutdown();
        }
        hookAsyncTaskField("sPoolWorkQueue", executor.getOtherPriorityQueue());
        hookAsyncTaskField("sThreadFactory", executor);
    }

    private static Object hookAsyncTaskField(String field, Object value) {
        Object old = null;
        try {
            final Field f = AsyncTask.class.getDeclaredField(field);
            f.setAccessible(true);
            old = f.get(AsyncTask.class);
            f.set(AsyncTask.class, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return old;
    }

    private void postInternal(Runnable r, Priority priority) {
        if (r == null)
            throw new NullPointerException("Runnable cannot be null");
        registerPriority(r, priority);
        mExecutorService.execute(r);
    }

    private void postDelayedInternal(final Runnable r, long delay, final Priority priority) {
        if (delay <= 0) {
            postInternal(r, priority);
        } else {
            final Runnable delayRun = new Runnable() {
                @Override
                public void run() {
                    mDelayRunnableMap.remove(r);
                    postInternal(r, priority);
                }
            };
            if (mMainHandler.postDelayed(delayRun, delay)) {
                mDelayRunnableMap.put(r, delayRun);
            }
        }
    }

    private void removeInternal(Runnable r) {
        mMainHandler.removeCallbacks(mDelayRunnableMap.remove(r));

        final BlockingQueue highQueue = mExecutorService.getHighPriorityQueue();
        final BlockingQueue otherQueue = mExecutorService.getOtherPriorityQueue();

        final Runnable realSerialR = mSerialExecutor.remove(r);
        highQueue.remove(realSerialR);
        otherQueue.remove(realSerialR);

        highQueue.remove(r);
        otherQueue.remove(r);
    }

    /**
     * 提交一个业务runnable
     */
    @Override
    public void post(Runnable r) {
        post(r, getRunnablePriority(r));
    }

    /**
     * 提交一个UI相关业务runnable，此业务优先级别最高，请根据业务的紧急程度添加
     */
    @Override
    public void postHighPriority(Runnable r) {
        post(r, Priority.HIGH);
    }

    /**
     * 提交一个普通优先级的后台Work
     */
    @Override
    public void postNormalPriority(Runnable r) {
        post(r, Priority.NORMAL);
    }

    /**
     * 提交一个低优先级的后台Work
     */
    @Override
    public void postLowPriority(Runnable r) {
        post(r, Priority.LOW);
    }

    @Override
    public void post(Runnable r, Priority priority) {
        postInternal(r, priority);
    }

    @Override
    public void postDelayed(Runnable r, long delay) {
        postDelayed(r, delay, getRunnablePriority(r));
    }

    /**
     * 延时提交业务runnable，runnable需设置好对应业务优先级，默认为Normal
     */
    @Override
    public void postDelayed(Runnable r, long delay, Priority priority) {
        postDelayedInternal(r, delay, priority);
    }

    @Override
    public void postSerial(Runnable r) {
        postSerial(r, getRunnablePriority(r));
    }

    /**
     * 顺序串行任务
     */
    @Override
    public void postSerial(Runnable r, Priority priority) {
        if (r == null)
            throw new NullPointerException("Runnable cannot be null");
        registerPriority(r, priority);
        mSerialExecutor.execute(r, priority);
    }

    /**
     * 从队列中移除相关业务runnable
     */
    @Override
    public void remove(Runnable r) {
        removeInternal(r);
    }

    /**
     * 在主线程中执行,如果当前是子线程,则执行{@link #postOnMain(Runnable)}
     */
    @Override
    public void runOnMain(Runnable r) {
        if (r == null)
            throw new NullPointerException("Runnable cannot be null");
        if (Looper.myLooper() == Looper.getMainLooper()) {
            r.run();
            return;
        }
        postOnMain(r);
    }

    /**
     * 提交到主线程Looper的消息队列中执行
     */
    @Override
    public void postOnMain(Runnable r) {
        if (r == null)
            throw new NullPointerException("Runnable cannot be null");
        mMainHandler.post(r);
    }

    @Override
    public void postOnMainDelayed(Runnable r, long delay) {
        if (r == null)
            throw new NullPointerException("Runnable cannot be null");
        mMainHandler.postDelayed(r, delay);
    }

    @Override
    public void removeOnMain(Runnable r) {
        if (r == null)
            throw new NullPointerException("Runnable cannot be null");
        mMainHandler.removeCallbacks(r);
    }

    @Override
    public ExecutorService getExecutorService() {
        return mExecutorService;
    }

    @Override
    public void registerPriority(Runnable r, Priority priority) {
        if (r == null || priority == null)
            throw new NullPointerException("params is null");
        mExecutorService.registerPriority(r, priority);
    }

    @Override
    public void unregisterPriority(Runnable r) {
        if (r == null)
            return;
        mExecutorService.unregisterPriority(r);
    }

    @Override
    public void registerPriority(Class<? extends Runnable> clazz, Priority priority) {
        if (clazz == null || priority == null)
            throw new NullPointerException("params is null");
        mExecutorService.registerPriority(clazz, priority);
    }

    @Override
    public void unregisterPriority(Class<? extends Runnable> clazz) {
        if (clazz == null)
            return;
        mExecutorService.unregisterPriority(clazz);
    }

    @Override
    public Priority getRunnablePriority(Runnable r) {
        if (r == null)
            return null;
        return mExecutorService.getRunnablePriority(r);
    }

    @Override
    public void addOnExecuteListener(OnExecuteListener listener) {
        mExecutorService.addOnExecuteListener(listener);
    }

    @Override
    public void removeOnExecuteListener(OnExecuteListener listener) {
        mExecutorService.removeOnExecuteListener(listener);
    }

    private class SerialExecutor {
        final ArrayDeque<Runnable> mTasks = new ArrayDeque<>();
        Runnable mActive;
        Map<Runnable, Runnable> mTaskMap = new WeakHashMap<>();

        synchronized void execute(@NonNull final Runnable r, Priority priority) {
            final Runnable realRun = new Runnable() {
                public void run() {
                    try {
                        r.run();
                    } finally {
                        scheduleNext();
                    }
                }
            };
            registerPriority(realRun, priority);
            mTaskMap.put(r, realRun);
            mTasks.offer(realRun);
            if (mActive == null) {
                scheduleNext();
            }
        }

        /**
         * @return 返回realRun
         */
        synchronized Runnable remove(Runnable r) {
            final Runnable realR = mTaskMap.remove(r);
            mTasks.remove(realR);
            return realR;
        }

        synchronized void scheduleNext() {
            if ((mActive = mTasks.poll()) != null) {
                mExecutorService.execute(mActive);
            }
        }
    }
}
