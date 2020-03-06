package com.ldlywt.androidadvance;

import com.ldlywt.androidadvance.exception.GlobalCrashException;
import com.ldlywt.androidadvance.http.OKHttpEngine;
import com.ldlywt.androidadvance.menory.MemoryHandler;
import com.ldlywt.base.BaseApp;
import com.ldlywt.base.XFrame;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class App extends BaseApp {

    public static boolean isLogin = true;

    @Override
    public void onCreate() {
        super.onCreate();
        initLog();
        GlobalCrashException.getInstance().init(this);
        XFrame.initXHttp(new OKHttpEngine());
        XFrame.initXLoadingView().setErrorViewResId(R.layout._loading_layout_error);
        /**
         * 初始化全局图片加载框架
         * GlideImageLoader为你的图片加载框架实现类
         */
//        XFrame.initXImageLoader(new GlideImageLoader(getApplicationContext());
        MemoryHandler.startMonitorLowMemory();
        MemoryHandler.setMemoryWarmingCallback(new MemoryHandler.IMemoryWarmingCallback() {
            @Override
            public void onLowMemory() {
                //内存占用超过80%是调用，释放一些内存占用
                //ImageLoader.clearAllMemoryCaches();
            }
        });
    }

    private void initLog() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 是否显示线程信息，默认为ture
                .methodCount(2)         // 显示的方法行数，默认为2
                .methodOffset(7)        // 隐藏内部方法调用到偏移量，默认为5
//                .logStrategy(customLog) // 更改要打印的日志策略。
                .tag("hello")   // 每个日志的全局标记。默认PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }
}
