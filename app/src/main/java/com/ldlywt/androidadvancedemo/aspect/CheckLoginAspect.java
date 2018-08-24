package com.ldlywt.androidadvancedemo.aspect;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ldlywt.base.App;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * <pre>
 *     author : wutao
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Aspect
public class CheckLoginAspect {
    private static final String TAG = CheckLoginAspect.class.getSimpleName();

    /**
     * 找到处理的切点
     * * *(..)  可以处理CheckLogin这个类所有的方法
     */
    @Pointcut("execution(@com.ldlywt.androidadvancedemo.aspect.CheckLogin  * *(..))")
    public void executionCheckLogin() {
    }

    /**
     * 处理切面
     *
     * @param joinPoint
     * @return
     */
    @Around("executionCheckLogin()")
    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckLogin annotation = signature.getMethod().getAnnotation(CheckLogin.class);
        if (annotation != null) {
            Context context = AspectUtils.getContext(joinPoint.getThis());
            if (App.isLogin) {
                Log.i(TAG, "checkLogin: 登录成功 ");
                Toast.makeText(context, "用户已经登录了", Toast.LENGTH_SHORT).show();
                return joinPoint.proceed();
            } else {
                Log.i(TAG, "checkLogin: 请登录");
                Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        return joinPoint.proceed();
    }
}
