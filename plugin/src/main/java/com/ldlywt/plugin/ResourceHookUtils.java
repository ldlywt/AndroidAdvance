package com.ldlywt.plugin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.Method;

/**
 * <pre>
 *     @author : wutao
 *     e-mail : 670831931@qq.com
 *     time   : 2020/03/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ResourceHookUtils {
    private final static String apkPath = "/sdcard/plugin.apk";
    private static Resources mResource;

    public static Resources getResource(Context context) {
        if (mResource == null) {
            mResource = loadResources(context);
        }
        return mResource;
    }

    private static Resources loadResources(Context context) {

        try {
            AssetManager assetManager = AssetManager.class.newInstance();

            Method addAssetPathMethod = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
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
