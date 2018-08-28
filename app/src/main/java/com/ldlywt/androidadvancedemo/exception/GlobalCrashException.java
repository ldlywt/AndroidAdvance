package com.ldlywt.androidadvancedemo.exception;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/28
 *     desc   : 全局异常捕获
 *     version: 1.0
 * </pre>
 */
public class GlobalCrashException implements Thread.UncaughtExceptionHandler {

    private static Context mContext;
    private static Thread.UncaughtExceptionHandler sDefaultUncaughtExceptionHandler;

    private GlobalCrashException() {
    }

    public static GlobalCrashException getInstance() {
        return Holder.sInstance;
    }

    public void init(Context context) {
        mContext = context;
        //或者系统默认的异常捕获机制
        sDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //将自定义的异常捕获设置为系统默认的
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        if (!handleException(e) && sDefaultUncaughtExceptionHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            sDefaultUncaughtExceptionHandler.uncaughtException(t, e);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.e(ex.getMessage());
            }
            //退出程序
            sDefaultUncaughtExceptionHandler.uncaughtException(t, e);
        }
    }

    /**
     * 自定义错误处理
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        final String msg = ex.getLocalizedMessage();
        if (msg == null) {
            return false;
        }

        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常！" + ex, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        return true;
    }

    private static class Holder {
        private static final GlobalCrashException sInstance = new GlobalCrashException();
    }

}