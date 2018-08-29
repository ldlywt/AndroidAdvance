package com.ldlywt.xeventbus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class XEventBus {

    private Handler mHandler;
    private ExecutorService mExecutorService;
    private Map<Object, List<SubscribeMethod>> mSubscribeMethodMap;

    private XEventBus() {
        mSubscribeMethodMap = new HashMap<>();
        mHandler = new Handler(Looper.getMainLooper());
        mExecutorService = Executors.newCachedThreadPool();

    }

    public static XEventBus getDefault() {
        return Holder.INSTANCE;
    }

    public void register(Object object) {
        List<SubscribeMethod> subscribeMethods = mSubscribeMethodMap.get(object);
        if (subscribeMethods == null) {
            subscribeMethods = getSubscribeMethods(object);
            mSubscribeMethodMap.put(object, subscribeMethods);
        }
    }

    private List<SubscribeMethod> getSubscribeMethods(Object object) {
        List<SubscribeMethod> list = new ArrayList<>();
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            if (clazz.getName().startsWith("java.")
                    || clazz.getName().startsWith("javax.")
                    || clazz.getName().startsWith("android.")) {//如果类全名以这些字符开头，则认为是jdk的，不是我们自定义的，自然没必要去拿注解
                break;
            }
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (subscribe == null) {
                    continue;
                }
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new RuntimeException("EventBus 只能接收到一个参数");
                }
                ThreadMode threadMode = subscribe.threadMode();
                SubscribeMethod subscribeMethod = new SubscribeMethod(method, threadMode, parameterTypes[0]);
                list.add(subscribeMethod);

            }
            clazz = clazz.getSuperclass();
        }
        return list;
    }

    public void unRegister(Object object) {
        if (mSubscribeMethodMap != null) {
            mSubscribeMethodMap.remove(object);
        }
    }

    public void post(Object event) {
        Iterator<Object> iterator = mSubscribeMethodMap.keySet().iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            List<SubscribeMethod> subscribeMethodList = mSubscribeMethodMap.get(object);
            for (SubscribeMethod subscribeMethod : subscribeMethodList) {
                //有两个Class类型的类象，一个是调用isAssignableFrom方法的类对象（后称对象a），以及方法中作为参数的这个类对象（称之为对象b），这两个对象如果满足以下条件则返回true，否则返回false：
                //    a对象所对应类信息是b对象所对应的类信息的父类或者是父接口，简单理解即a是b的父类或接口
                //    a对象所对应类信息与b对象所对应的类信息相同，简单理解即a和b为同一个类或同一个
                if (subscribeMethod.getEventType().isAssignableFrom(event.getClass())) {
                    switch (subscribeMethod.getThreadMode()) {
                        case POST:
                            invoke(subscribeMethod, object, event);
                            break;
                        case MAIN:
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                                invoke(subscribeMethod, object, event);
                            } else {
                                mHandler.post(() -> invoke(subscribeMethod, object, event));
                            }
                            break;
                        case ASYNC:
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                                mExecutorService.execute(() -> {
                                    invoke(subscribeMethod, object, event);
                                });
                            } else {
                                invoke(subscribeMethod, object, event);
                            }
                            break;

                    }
                }

            }

        }
    }

    private void invoke(SubscribeMethod subscribeMethod, Object obj, Object event) {
        Method method = subscribeMethod.getMethod();
        try {
            method.invoke(obj, event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static class Holder {
        private static final XEventBus INSTANCE = new XEventBus();
    }


}
