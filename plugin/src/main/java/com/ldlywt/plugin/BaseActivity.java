package com.ldlywt.plugin;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

import java.lang.reflect.Field;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * <pre>
 *     @author : wutao
 *     e-mail : 670831931@qq.com
 *     time   : 2020/03/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class BaseActivity extends AppCompatActivity {

    protected Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceResource();

    }

    /**
     * 方式二，Activity可以继承AppCompatActivity
     */
    private void replaceResource() {
        Resources resource = ResourceHookUtils.getResource(getApplication());
        // 替换这个 context 的resource 为我们自己写的 resource
        mContext = new ContextThemeWrapper(getBaseContext(), 0);
        Class<? extends Context> clazz = mContext.getClass();
        try {
            Field mResourcesField = clazz.getDeclaredField("mResources");
            mResourcesField.setAccessible(true);
            mResourcesField.set(mContext, resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 第一种方式只能继承自Activity
     */
//    @Override
//    public Resources getResources() {
//        if (getApplication() != null && getApplication().getResources() != null) {
//            //这个实际返回的是我们自己创建的 resources
//            return getApplication().getResources();
//        }
//        return super.getResources();
//    }
}
