package com.ldlywt.hookdemo;

import android.app.Application;

/**
 * <pre>
 *     @author : wutao
 *     e-mail : 670831931@qq.com
 *     time   : 2020/03/01
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class App extends Application {

//    private Resources mResources;

    @Override
    public void onCreate() {
        super.onCreate();
        LoadUtil.loadClass(this);
        //方式一
//        mResources = LoadUtil.loadResource(this);
        HookUtil.hookAMS();
        HookUtil.hookHandler();
    }

//    @Override
//    public Resources getResources() {
//        return mResources == null ? super.getResources() : mResources;
//    }
}
