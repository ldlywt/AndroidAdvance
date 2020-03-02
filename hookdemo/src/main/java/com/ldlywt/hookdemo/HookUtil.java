package com.ldlywt.hookdemo;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;


public class HookUtil {


    private static final String TARGET_INTENT = "target_intent";

    public static void hookAMS() {
        try {

            Object singleton = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // 获取Singleton对象
                Class<?> clazz = Class.forName("android.app.ActivityManager");
                Field singletonField = clazz.getDeclaredField("IActivityManagerSingleton");
                singletonField.setAccessible(true);
                singleton = singletonField.get(null);
            } else {
                Class<?> clazz = Class.forName("android.app.ActivityManagerNative");
                Field singletonField = clazz.getDeclaredField("gDefault");
                singletonField.setAccessible(true);
                singleton = singletonField.get(null);
            }

            // 获取IActivityManager 对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            final Object mInstance = mInstanceField.get(singleton);


            Class<?> iActivityManagerClass = Class.forName("android.app.IActivityManager");

            Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{iActivityManagerClass}, new InvocationHandler() {

                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            // IActivityManager 的方法执行的时候，都会先跑这儿
                            if ("startActivity".equals(method.getName())) {
                                Log.i("wutao", "hook到 : startActivity");
                                // 替换Intent
                                int index = 0;

                                for (int i = 0; i < args.length; i++) {
                                    if (args[i] instanceof Intent) {
                                        index = i;
                                        break;
                                    }
                                }
                                // 启动插件的intent
                                Intent intent = (Intent) args[index];

                                Intent proxyIntent = new Intent();

                                proxyIntent.setClassName("com.ldlywt.hookdemo",
                                        "com.ldlywt.hookdemo.ui.ProxyActivity");

                                // 保存原来的
                                proxyIntent.putExtra(TARGET_INTENT, intent);

                                args[index] = proxyIntent;
                            }
                            return method.invoke(mInstance, args);
                        }

                    });

            // 替换系统的 IActivityManager对象
            mInstanceField.set(singleton, proxyInstance);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void hookHandler() {

        try {
            Class<?> clazz = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = clazz.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object activityThread = sCurrentActivityThreadField.get(null);

            Field mHField = clazz.getDeclaredField("mH");
            mHField.setAccessible(true);
            Object mH = mHField.get(activityThread);

            // new 一个 Callback 替换系统的 mCallback对象
            Class<?> handlerClass = Class.forName("android.os.Handler");
            Field mCallbackField = handlerClass.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);
            mCallbackField.set(mH, new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message msg) {
                    // 将Intent换回来
                    switch (msg.what) {
                        case 100:
                            try {
                                // 获取ActivityClientRecord中的intent对象
                                Field intentField = msg.obj.getClass().getDeclaredField("intent");
                                intentField.setAccessible(true);
                                Intent proxyIntent = (Intent) intentField.get(msg.obj);

                                // 拿到插件的Intent
                                Intent intent = proxyIntent.getParcelableExtra(TARGET_INTENT);
                                Log.i("wutao", "handleMessage: " + intent.toString());

                                // 替换回来
//                                proxyIntent.setComponent(intent.getComponent());
                                intentField.set(msg.obj, intent);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 159:
                            try {
                                Class<?> clazz = Class.forName("android.app.servertransaction.ClientTransaction");
                                Field mActivityCallbacksField = clazz.getDeclaredField("mActivityCallbacks");
                                mActivityCallbacksField.setAccessible(true);

                                List activityCallbacks = (List) mActivityCallbacksField.get(msg.obj);
                                for (int i = 0; i < activityCallbacks.size(); i++) {
                                    if (activityCallbacks.get(i).getClass().getName()
                                            .equals("android.app.servertransaction.LaunchActivityItem")) {
                                        Object launchActivityItem = activityCallbacks.get(i);
                                        Field mIntentField = launchActivityItem.getClass().getDeclaredField("mIntent");
                                        mIntentField.setAccessible(true);
                                        Intent proxyIntent = (Intent) mIntentField.get(launchActivityItem);

                                        //插件的intent
                                        Intent intent = proxyIntent.getParcelableExtra(TARGET_INTENT);

                                        if (intent != null) {
                                            mIntentField.set(launchActivityItem, intent);
                                        }
                                        break;
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
