package com.ldlywt.androidadvancedemo.aspect;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ldlywt.base.utils.PermissionManager;

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
 *     desc   : 检查权限的切面
 *     version: 1.0
 * </pre>
 */
@Aspect
public class CheckPermissionAspect {

    private static final String TAG = CheckPermissionAspect.class.getSimpleName();

    /**
     * 找到处理的切点
     * * *(..)  可以处理CheckLogin这个类所有的方法
     */
    @Pointcut("execution(@com.ldlywt.androidadvancedemo.aspect.CheckPermission  * *(..))")
    public void executionCheckPermission() {
    }

    /**
     * 处理切面
     *
     * @param joinPoint
     * @return
     */
    @Around("executionCheckPermission()")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckPermission annotation = signature.getMethod().getAnnotation(CheckPermission.class);
        if (annotation != null) {
            String permission = annotation.value();
            Context context = AspectUtils.getContext(joinPoint.getThis());
            Object o = null;
            if (PermissionManager.getInnerInstance().checkPermission(context,permission)) {
                o = joinPoint.proceed();
                Log.i(TAG, "有权限");
            } else {
                Log.i(TAG, "没有权限，不给用");
                Toast.makeText(context, "没有权限不要用", Toast.LENGTH_SHORT).show();
            }
            return o;

        }
        return null;
    }


}
