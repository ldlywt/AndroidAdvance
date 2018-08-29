package com.ldlywt.xeventbus;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public enum  ThreadMode {

    /**
     * 将事件执行在UI线程
     */
    MAIN,
    /**
     * 在发布线程执行
     */
    POST,
    /**
     * 将事件执行在一个子线程中
     */
    ASYNC

}
