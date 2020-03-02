package com.ldlywt.hookdemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class LoadUtil {

    private final static String apkPath = "/sdcard/plugin.apk";


    public static void loadClass(Context context) {

        try {
            // private final DexPathList pathList;
            Class<?> baseDexClassLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListField = baseDexClassLoaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);

            // private Element[] dexElements;
            Class<?> dexPathListClass = Class.forName("dalvik.system.DexPathList");
            Field dexElementsField = dexPathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);

            /**
             * 插件
             */
            // 创建插件的 DexClassLoader 类加载器，然后通过反射获取插件的 dexElements 值
            // 插件的
            DexClassLoader dexClassLoader = new DexClassLoader(apkPath, context.getCacheDir().getAbsolutePath(),
                    null, context.getClassLoader());

            Object pluginPathList = pathListField.get(dexClassLoader);
            // 拿到了插件的 dexElements
            Object[] pluginDexElements = (Object[]) dexElementsField.get(pluginPathList);

            /**
             * 宿主
             */
            PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();

            Object hostPathList = pathListField.get(pathClassLoader);
            // 拿到了宿主的 dexElements
            Object[] hostDexElements = (Object[]) dexElementsField.get(hostPathList);


            /**
             * 创建数组
             */
            // Element[]
            Object[] dexElements = (Object[]) Array.newInstance(pluginDexElements.getClass().getComponentType(),
                    hostDexElements.length + pluginDexElements.length);

            System.arraycopy(hostDexElements, 0, dexElements, 0, hostDexElements.length);
            System.arraycopy(pluginDexElements, 0, dexElements, hostDexElements.length, pluginDexElements.length);

            // 将创建的 dexElements 放到宿主的 dexElements中
            // 宿主的dexElements = 新的dexElements
            dexElementsField.set(hostPathList, dexElements);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static Resources loadResource(Context context) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();

            Method addAssetPathMethod = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPathMethod.setAccessible(true);
            // 参数就是插件的资源路径
            addAssetPathMethod.invoke(assetManager, apkPath);

            Resources resources = context.getResources();

            return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
