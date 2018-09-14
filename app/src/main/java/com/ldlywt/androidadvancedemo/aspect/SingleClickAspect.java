package com.ldlywt.androidadvancedemo.aspect;

import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * <pre>
 *     author : wutao
 *     e-mail : ldlywt@163.com
 *     time   : 2018/09/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Aspect
public class SingleClickAspect {
    private static final long DEFAULT_TIME_INTERVAL = 5000;

    @Pointcut("execution(@com.ldlywt.androidadvancedemo.aspect.SingleClick  * *(..))")
    public void executionSingleClick() {
    }

    @Around("executionSingleClick()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = (View)arg;
                break;
            }
        }
        if (view == null) {
            return;
        }

        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        SingleClick annotation = signature.getMethod().getAnnotation(SingleClick.class);
        if (annotation != null) {
            if (!isFastDoubleClick(view, annotation.value())) {
                joinPoint.proceed();
                LogUtils.i("正常点击");
            } else {
                LogUtils.i("你点击的太快了");
                ToastUtils.showShort("请不要这么快点击按钮");
            }
        }
    }

    /**
     * 最近一次点击的时间
     */
    private long mLastClickTime;
    /**
     * 最近一次点击的控件ID
     */
    private int mLastClickViewId;

    /**
     * 是否是快速点击
     *
     * @param view  点击的控件
     * @param intervalMillis  时间间期（毫秒）
     * @return  true:是，false:不是
     */
    private boolean isFastDoubleClick(View view, long intervalMillis) {
        int viewId = view.getId();
        long time = System.currentTimeMillis();
        long timeInterval = Math.abs(time - mLastClickTime);
        if (timeInterval < intervalMillis && viewId == mLastClickViewId) {
            return true;
        } else {
            mLastClickTime = time;
            mLastClickViewId = viewId;
            return false;
        }
    }

}
