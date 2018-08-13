package com.ldlywt.androidadvancedemo.aspect;

import android.util.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/13
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Aspect
public class BehaviorTraceAspect {

    private static final String TAG = BehaviorTraceAspect.class.getSimpleName();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 找到处理的切点
     * * *(..)  可以处理CheckLogin这个类所有的方法
     */
    @Pointcut("execution(@com.ldlywt.androidadvancedemo.aspect.BehaviorTrace  * *(..))")
    public void executionBehaviorTrace() {
    }

    /**
     * 处理切面
     *
     * @param joinPoint
     * @return
     */
    @Around("executionBehaviorTrace()")
    public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.d(TAG,"xxxxx");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        BehaviorTrace annotation = signature.getMethod().getAnnotation(BehaviorTrace.class);
        String content = annotation.value();
        int type = annotation.type();
        long begin = System.currentTimeMillis();
        Log.d(TAG, content + " dealPoint begin: " + simpleDateFormat.format(new Date()));
        Object object = joinPoint.proceed();
        Log.d(TAG, content + " dealPoint end: " + (System.currentTimeMillis() - begin) + "ms");
        return object;
    }
}
