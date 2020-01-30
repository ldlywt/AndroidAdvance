package com.fastaac;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <pre>
 *     @author : wutao
 *     e-mail : 670831931@qq.com
 *     time   : 2020/01/30
 *     desc   : Hook Activity的启动方法
 *     version: 1.0
 * </pre>
 */
public class InstrumentationProxy extends Instrumentation {
    private Instrumentation mInstrumentation;

    public InstrumentationProxy(Instrumentation instrumentation) {
        mInstrumentation = instrumentation;
    }

    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target,
                                            Intent intent, int requestCode, Bundle bundle) {
        Log.i("wutao", "Hook成功 : " + who);
        try {
            Method execStartActivity = Instrumentation.class.getDeclaredMethod("execStartActivity",
                    Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class);
            ActivityResult activityResult = (ActivityResult) execStartActivity.invoke(mInstrumentation,
                    who, contextThread, token, target, intent, requestCode, bundle);
            return activityResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void replaceActivityInstrumentation(Activity activity) {
        try {
            Field field = Activity.class.getDeclaredField("mInstrumentation");
            field.setAccessible(true);
            Instrumentation instrumentation = (Instrumentation) field.get(activity);
            Instrumentation instrumentationProx = new InstrumentationProxy(instrumentation);
            field.set(activity, instrumentationProx);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
