package com.ldlywt.ioc.manager;


import android.view.View;
import android.widget.Toast;

import com.ldlywt.ioc.annomation.event.OnClick;
import com.ldlywt.ioc.annomation.event.OnLongClick;
import com.ldlywt.ioc.annomation.network.CheckNet;
import com.ldlywt.ioc.annomation.resouces.ColorById;
import com.ldlywt.ioc.annomation.resouces.ContentViewById;
import com.ldlywt.ioc.annomation.resouces.StringById;
import com.ldlywt.ioc.annomation.resouces.ViewById;
import com.ldlywt.ioc.utils.LighterReflectionException;
import com.ldlywt.ioc.utils.NetUtils;
import com.ldlywt.ioc.utils.ReflectionUtil;

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
     */
    public static void injectFieldById(ViewManager viewManager, Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                injectViewById(viewManager, object, field);
                injectStringById(viewManager, object, field);
                injectColorById(viewManager, object, field);
            }

        }
    }

    private static void injectViewById(ViewManager viewManager, Object object, Field field) {
        ViewById viewById = field.getAnnotation(ViewById.class);
        if (viewById != null) {
            int viewId = viewById.value();
            View view = viewManager.findViewById(viewId);
            try {
                ReflectionUtil.setValue(field, object, view);
            } catch (LighterReflectionException e) {
                e.printStackTrace();
            }
        }
    }

    private static void injectStringById(ViewManager viewManager, Object object, Field field) {
        StringById stringById = field.getAnnotation(StringById.class);
        if (stringById != null) {
            int stringId = stringById.value();
            String string = viewManager.getString(stringId);
            try {
                ReflectionUtil.setValue(field, object, string);
            } catch (LighterReflectionException e) {
                e.printStackTrace();
            }
        }
    }

    private static void injectColorById(ViewManager viewManager, Object object, Field field) {
        ColorById colorById = field.getAnnotation(ColorById.class);
        if (colorById != null) {
            int colorId = colorById.value();
            int color = viewManager.getColor(colorId);
            try {
                ReflectionUtil.setValue(field, object, color);
            } catch (LighterReflectionException e) {
                e.printStackTrace();
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
    private static void injectOnClick(ViewManager viewManager, final Object object) {
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        if (methods != null) {
            for (final Method method : methods) {
                OnClick onClick = method.getAnnotation(OnClick.class);
                if (onClick != null) {
                    int[] viewIds = onClick.value();
                    for (int viewId : viewIds) {
                        View view = viewManager.findViewById(viewId);
                        final boolean isCheckNet = method.getAnnotation(CheckNet.class) != null;
                        if (view != null) {
                            view.setOnClickListener(v -> {
                                if (isCheckNet) {
                                    if (!NetUtils.isNetworkAvailable(v.getContext())) {
                                        Toast.makeText(v.getContext(), "网络不可用", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                try {
                                    ReflectionUtil.invoke(method, object, v);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
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
                            view.setOnLongClickListener(v -> {
                                if (isCheckNet) {
                                    if (!NetUtils.isNetworkAvailable(v.getContext())) {
                                        Toast.makeText(v.getContext(), "网络不可用", Toast.LENGTH_SHORT).show();
                                        return true;
                                    }
                                }
                                try {
                                    ReflectionUtil.invoke(method, object, v);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return true;
                            });
                        }
                    }
                }
            }
        }
    }
}
