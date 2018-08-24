package com.ldlywt.androidadvancedemo.aspect;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.ldlywt.androidadvancedemo.utils.NetworkUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * <pre>
 *     author : wutao
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/12
 *     desc   : 检查网络是都可以用的切面
 *     version: 1.0
 * </pre>
 */
@Aspect
public class CheckNetworkAspect {
    private static final String TAG = CheckNetworkAspect.class.getSimpleName();

    /**
     * 找到处理的切点
     *   * *(..)  “**”表示是任意包名   “..”表示任意类型任意多个参数
     */
    @Pointcut("execution(@com.ldlywt.androidadvancedemo.aspect.CheckNetwork  * *(..))")
    public void executionCheckNetwork() {
    }

    /**
     * 处理切面
     *
     * @param joinPoint
     * @return
     */
    @Around("executionCheckNetwork()")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckNetwork annotation = signature.getMethod().getAnnotation(CheckNetwork.class);
        if (annotation != null) {
            Context context = AspectUtils.getContext(joinPoint.getThis());
            if (NetworkUtils.isNetworkAvailable(context)) {
                Toast.makeText(context, "当前网络正常", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "此时没有网络连接", Toast.LENGTH_SHORT).show();
            }
            return joinPoint.proceed();
        }
        return null;
    }


}
