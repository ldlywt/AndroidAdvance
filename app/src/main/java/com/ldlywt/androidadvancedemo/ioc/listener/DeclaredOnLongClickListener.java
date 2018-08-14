package com.ldlywt.androidadvancedemo.ioc.listener;

import android.view.View;
import android.widget.Toast;

import com.ldlywt.androidadvancedemo.utils.NetworkUtils;

import java.lang.reflect.InvocationTargetException;
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
public class DeclaredOnLongClickListener implements View.OnLongClickListener {

    private Method mMethod;
    private Object mObject;
    private boolean mIsCheckNet;

    public DeclaredOnLongClickListener(Method method, Object object, boolean isCheckNet) {
        this.mMethod = method;
        this.mObject = object;
        this.mIsCheckNet = isCheckNet;
    }


    @Override
    public boolean onLongClick(View v) {
        if (mIsCheckNet) {
            if (!NetworkUtils.isNetworkAvailable(v.getContext())) {
                Toast.makeText(v.getContext(), "网络不可用", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        try {
            mMethod.setAccessible(true);
            mMethod.invoke(mObject, v);

        } catch (Exception e) {
            try {
                mMethod.invoke(mObject, (Object) null);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return true;
    }
}
