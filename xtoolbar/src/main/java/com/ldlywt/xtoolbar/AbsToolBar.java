package com.ldlywt.xtoolbar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * <pre>
 *     author : wutao
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class AbsToolBar implements IToolBar {

    private Builder.Params mParams;

    protected AbsToolBar(Builder.Params params) {
        mParams = params;
        bindView();
    }

    private void bindView() {
        if (mParams.viewGroup == null) {
            if (mParams.context instanceof Activity) {
                ViewGroup decorView = (ViewGroup) ((Activity) mParams.context).getWindow().getDecorView();
                mParams.viewGroup = (ViewGroup) decorView.getChildAt(0);
            }
            if (mParams.viewGroup == null) {
                throw new RuntimeException("navigationView only use in activity");
            }
        }
        View view = LayoutInflater.from(mParams.context).inflate(getLayoutRes(), mParams.viewGroup, false);
        mParams.viewGroup.addView(view,0);
        apply();
    }


    public abstract int getLayoutRes();

    public abstract void apply();

    public abstract static class Builder{

        public abstract AbsToolBar builder();


        public static class Params{

            private Context context;
            private ViewGroup viewGroup;

            Params(Context context, ViewGroup viewGroup){
                this.context = context;
                this.viewGroup = viewGroup;
            }

        }
    }
}
