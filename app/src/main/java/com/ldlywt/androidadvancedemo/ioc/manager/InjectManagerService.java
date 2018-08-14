package com.ldlywt.androidadvancedemo.ioc.manager;

import android.view.View;

import com.ldlywt.androidadvancedemo.ioc.annomation.event.OnClick;
import com.ldlywt.androidadvancedemo.ioc.annomation.event.OnLongClick;
import com.ldlywt.androidadvancedemo.ioc.annomation.network.CheckNet;
import com.ldlywt.androidadvancedemo.ioc.annomation.resouces.ColorById;
import com.ldlywt.androidadvancedemo.ioc.annomation.resouces.ContentViewById;
import com.ldlywt.androidadvancedemo.ioc.annomation.resouces.StringById;
import com.ldlywt.androidadvancedemo.ioc.annomation.resouces.ViewById;
import com.ldlywt.androidadvancedemo.ioc.listener.DeclaredOnClickListener;
import com.ldlywt.androidadvancedemo.ioc.listener.DeclaredOnLongClickListener;
import com.ldlywt.androidadvancedemo.ioc.view.ViewManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class InjectManagerService {

    public static void injectContentView(ViewManager viewManager, Object object) {
        injectContentViewById(viewManager, object);
    }

    private static void injectContentViewById(ViewManager viewManager, Object object) {
        Class<?> clazz = object.getClass();
        ContentViewById annotation = clazz.getAnnotation(ContentViewById.class);
        if (annotation != null) {
            int layoutId = annotation.value();
            viewManager.setContentView(layoutId);
        }
    }

    /**
     * 注入变量
     *
     * @param viewManager
     * @param object
     */
    public static void injectField(ViewManager viewManager, Object object) {
        injectFieldById(viewManager, object);
    }

    private static void injectFieldById(ViewManager viewManager, Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {

                ViewById viewById = field.getAnnotation(ViewById.class);
                if (viewById != null) {
                    int viewId = viewById.value();
                    View view = viewManager.findViewById(viewId);
                    //作用就是让我们在用反射时访问私有变量
                    field.setAccessible(true);
                    try {
                        //动态注入到变量中,将指定对象变量上此 Field 对象表示的字段设置为指定的新值。
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                StringById stringById = field.getAnnotation(StringById.class);
                if (stringById != null) {
                    int stringId = stringById.value();
                    String string = viewManager.getString(stringId);
                    field.setAccessible(true);
                    try {
                        field.set(object, string);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                ColorById colorById = field.getAnnotation(ColorById.class);
                if (colorById != null) {
                    int colorId = colorById.value();
                    int color = viewManager.getColor(colorId);
                    try {
                        field.setAccessible(true);
                        field.set(object, color);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    /**
     * 注入事件
     *
     * @param viewManager
     * @param object
     */
    public static void injectEvent(ViewManager viewManager, Object object) {
        injectOnClick(viewManager, object);
        injectOnLongClick(viewManager, object);
    }

    /**
     * 注入点击事件
     *
     * @param viewManager
     * @param object
     */
    private static void injectOnClick(ViewManager viewManager, Object object) {
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        if (methods != null) {
            for (Method method : methods) {
                OnClick onClick = method.getAnnotation(OnClick.class);
                if (onClick != null) {
                    int[] viewIds = onClick.value();
                    for (int viewId : viewIds) {
                        View view = viewManager.findViewById(viewId);
                        boolean isCheckNet = method.getAnnotation(CheckNet.class) != null;
                        if (view != null) {
                            view.setOnClickListener(new DeclaredOnClickListener(method, object, isCheckNet));
                        }
                    }
                }
            }
        }


    }

    /**
     * 注入长按点击事件
     *
     * @param viewManager
     * @param object
     */
    private static void injectOnLongClick(ViewManager viewManager, Object object) {
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        if (methods != null) {
            for (Method method : methods) {
                OnLongClick onLongClick = method.getAnnotation(OnLongClick.class);
                if (onLongClick != null) {
                    int[] viewIds = onLongClick.value();
                    for (int viewId : viewIds) {
                        View view = viewManager.findViewById(viewId);
                        //检查网络
                        boolean isCheckNet = method.getAnnotation(CheckNet.class) != null;
                        if (view != null) {
                            view.setOnLongClickListener(new DeclaredOnLongClickListener(method, object, isCheckNet));
                        }
                    }
                }
            }
        }
    }
}
