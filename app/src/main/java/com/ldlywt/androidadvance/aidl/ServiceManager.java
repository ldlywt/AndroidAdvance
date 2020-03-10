package com.ldlywt.androidadvance.aidl;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;


/**
 * <pre>
 *     @author : wutao
 *     e-mail : 670831931@qq.com
 *     time   : 2020/03/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ServiceManager {

    private static IServiceManager sServiceManager;
    private static final String TAG = "ServiceManager";
    /**
     * Cache for the "well known" services, such as WM and AM.
     */
    private static HashMap<String, IBinder> sCache = new HashMap<String, IBinder>();

    private static IServiceManager getIServiceManager() {
        if (sServiceManager != null) {
            return sServiceManager;
        }
        sServiceManager = IServiceManager.Stub.asInterface(BinderInternal.getContextObject());
        return sServiceManager;
    }

    public static void addService(String name, IBinder service) {
        try {
            getIServiceManager().addService(name, service, false, IServiceManager.DUMP_FLAG_PRIORITY_DEFAULT);
        } catch (RemoteException e) {
            Log.e(TAG, "error in addService", e);
        }
    }

    public static IBinder getService(String name) {
        try {
            IBinder service = sCache.get(name);
            if (service != null) {
                return service;
            } else {
//                return Binder.allowBlocking(rawGetService(name));
                return null;
            }
        } catch (Exception e) {
            Log.e(TAG, "error in getService", e);
        }
        return null;
    }

    /**
     * Retrieve an existing service called @a name from the
     * service manager.  Non-blocking.
     */
    public static IBinder checkService(String name) {
        try {
            IBinder service = sCache.get(name);
            if (service != null) {
                return service;
            } else {
//                return Binder.allowBlocking(getIServiceManager().checkService(name));
                return null;
            }
        } catch (Exception e) {
            Log.e(TAG, "error in checkService", e);
            return null;
        }
    }

    public static String[] listServices() {
        try {
            return getIServiceManager().listServices(0);
        } catch (RemoteException e) {
            Log.e(TAG, "error in listServices", e);
            return null;
        }
    }

    public static void initServiceCache(Map<String, IBinder> cache) {
        if (sCache.size() != 0) {
            throw new IllegalStateException("setServiceCache may only be called once");
        }
        sCache.putAll(cache);
    }

}

