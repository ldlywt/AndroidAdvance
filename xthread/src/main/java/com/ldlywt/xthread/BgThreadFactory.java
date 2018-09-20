package com.ldlywt.xthread;

import android.os.Process;
import android.support.annotation.NonNull;

import java.util.concurrent.ThreadFactory;

/**
 * 后台线程优先级的ThreadFactory
 */
public class BgThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(@NonNull Runnable r) {
        return new BgThread(r);
    }

    static class BgThread extends Thread {
        BgThread(Runnable target) {
            super(target);
        }

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            super.run();
        }
    }
}
