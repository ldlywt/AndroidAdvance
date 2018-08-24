package com.ldlywt.androidadvancedemo;

import com.ldlywt.base.BaseApp;
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
    @Override
    public void onCreate() {
        super.onCreate();
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 是否显示线程信息，默认为ture
                .methodCount(2)         // 显示的方法行数，默认为2
                .methodOffset(7)        // 隐藏内部方法调用到偏移量，默认为5
//                .logStrategy(customLog) // 更改要打印的日志策略。
                .tag("My custom tag")   // 每个日志的全局标记。默认PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }
}
