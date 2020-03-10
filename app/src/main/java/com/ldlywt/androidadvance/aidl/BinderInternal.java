package com.ldlywt.androidadvance.aidl;

import android.os.IBinder;

/**
 * <pre>
 *     @author : wutao
 *     e-mail : 670831931@qq.com
 *     time   : 2020/03/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class BinderInternal {

    /**
     * Return the global "context object" of the system.  This is usually
     * an implementation of IServiceManager, which you can use to find
     * other services.
     */
    public static final native IBinder getContextObject();
}
