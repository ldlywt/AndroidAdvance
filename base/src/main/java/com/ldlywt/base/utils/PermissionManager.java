package com.ldlywt.base.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

/**
 * <pre>
 *     author : wutao
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/12
 *     desc   : 临时使用
 *     version: 1.0
 * </pre>
 */
public class PermissionManager {
    private static volatile PermissionManager permissionManager;

    public PermissionManager(){}

    //DCL单例模式
    public static PermissionManager getInstance(){
        if (permissionManager == null){
            synchronized (PermissionManager.class){
                if (permissionManager == null){
                    permissionManager = new PermissionManager();
                }
            }
        }
        return permissionManager;
    }

    private static class InnerInsatance{
        public static final PermissionManager instance = new PermissionManager();
    }

    //内部类单例模式
    public static PermissionManager getInnerInstance(){
        synchronized (PermissionManager.class){
            return InnerInsatance.instance;
        }
    }

    public boolean checkPermission(Context context, String permission){
        Log.i("tag00","检查的权限："+permission);
        if (ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
//        if (permission.equals("android.permission.CAMERA")){
//            return true;
//        }
        return false;
    }
}
