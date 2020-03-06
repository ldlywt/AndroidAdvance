package com.ldlywt.androidadvance.menory;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

/**
 * <pre>
 *     @author : wutao
 *     e-mail : 670831931@qq.com
 *     time   : 2020/02/11
 *     desc   : 触顶率：可以反映 Java 内存的使用情况，如果超过 85% 最大堆限制，GC 会变得更加频繁，容易造成 OOM 和卡顿。
 *              内存 UV 触顶率 = Java 堆占用超过最大堆限制的 85% 的 UV / 采集 UV
 *     version: 1.0
 * </pre>
 */
public class MemoryHandler {
    private static Handler lowMemoryMonitorHandler;
    // 每隔一分钟监控内存
    private static final int MEMORY_MONITOR_INTERVAL = 1000 * 60;
    private static IMemoryWarmingCallback mIMemoryWarmingCallback;

    /**
     * 开启低内存监测，如果低内存了，作出相应的反应
     */
    public static void startMonitorLowMemory() {
        HandlerThread thread = new HandlerThread("thread_monitor_low_memory");
        thread.start();
        lowMemoryMonitorHandler = new Handler(thread.getLooper());
        lowMemoryMonitorHandler.postDelayed(releaseMemoryCacheRunner, MEMORY_MONITOR_INTERVAL);
    }

    /**
     * 如果已用内存达到了总的 80%时，就清空缓存
     */
    private static Runnable releaseMemoryCacheRunner = new Runnable() {
        @Override
        public void run() {

            long javaMax = Runtime.getRuntime().maxMemory();
            long javaTotal = Runtime.getRuntime().totalMemory();
            long javaUsed = javaTotal - Runtime.getRuntime().freeMemory();
            Log.i("wutao", "内存监控为：已使用： " + javaUsed + "  总共大小： " + javaMax);
            // Java 内存使用超过最大限制的 85%
            if (Double.compare(javaUsed, javaMax * 0.8) == 1) {
                if (mIMemoryWarmingCallback != null) {
                    mIMemoryWarmingCallback.onLowMemory();
                }
            }
            lowMemoryMonitorHandler.postDelayed(releaseMemoryCacheRunner, MEMORY_MONITOR_INTERVAL);
        }
    };

    public static void setMemoryWarmingCallback(IMemoryWarmingCallback callback) {
        mIMemoryWarmingCallback = callback;
    }

    public interface IMemoryWarmingCallback {
        /**
         * 内存占用超过80%是调用，释放一些内存占用
         */
        void onLowMemory();
    }
}
