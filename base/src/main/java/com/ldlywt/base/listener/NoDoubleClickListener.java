package com.ldlywt.base.listener;

import android.view.View;


/**
 * <pre>
 *     author : wutao
 *     time   : 2018/11/16
 *     desc   :
 * </pre>
 */
public abstract class NoDoubleClickListener implements View.OnClickListener {


    private static final int MIN_CLICK_DELAY_TIME = 1000;//这里设置不能超过多长时间
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        } else {
            onIsDoubleClick(v);
        }
    }

    protected abstract void onNoDoubleClick(View v);

    protected abstract void onIsDoubleClick(View v);
}