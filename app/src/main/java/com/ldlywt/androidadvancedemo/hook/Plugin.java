package com.ldlywt.androidadvancedemo.hook;

import android.content.Context;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * <pre>
 *     @author : wutao
 *     e-mail : 670831931@qq.com
 *     time   : 2020/02/28
 *     desc   : 插件化的基础，来源于享学课堂
 *     version: 1.0
 * </pre>
 */
public class Plugin {

    private final static String apkPath = "/sdcard/plugin-debug.apk";

    public static void loadPlugin(Context context) {
        try {
            Class<?> baseDexClassLoader = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListField = baseDexClassLoader.getDeclaredField("pathList");
            pathListField.setAccessible(true);

            Class<?> dexPathListClass = Class.forName("dalvik.system.DexPathList");
            Field dexElementsField = dexPathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);

            //插件的dexElements数组
            DexClassLoader dexClassLoader = new DexClassLoader(apkPath, context.getCacheDir().getAbsolutePath(), null, context.getClassLoader());
            Object pluginPathList = pathListField.get(dexClassLoader);
            // 拿到了插件的 dexElements
            Object[] pluginDexElements = (Object[]) dexElementsField.get(pluginPathList);

            //宿主的dexElements数组
            PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
            Object hostPathList = pathListField.get(pathClassLoader);
            // 拿到了宿主的 dexElements
            Object[] hostDexElements = (Object[]) dexElementsField.get(hostPathList);

            //创建一个大的数组
            Object[] dexElements = (Object[]) Array.newInstance(hostDexElements.getClass().getComponentType(), pluginDexElements.length + hostDexElements.length);
            System.arraycopy(hostDexElements, 0, dexElements, 0, hostDexElements.length);
            System.arraycopy(pluginDexElements, 0, dexElements, hostDexElements.length, pluginDexElements.length);

            //将新的数组重新复制给宿主
            dexElementsField.set(hostPathList, dexElements);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
