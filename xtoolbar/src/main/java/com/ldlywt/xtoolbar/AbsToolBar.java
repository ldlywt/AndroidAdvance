package com.ldlywt.xtoolbar;

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
        View view = LayoutInflater.from(mParams.mContext).inflate(getLayoutRes(), mParams.mViewGroup, false);
        mParams.mViewGroup.addView(view,0);
        apply();
    }


    public abstract int getLayoutRes();

    public abstract void apply();

    public abstract static class Builder{


        public Builder(Context context, ViewGroup viewGroup){

        }

        public abstract AbsToolBar builder();


        public static class Params{

            private Context mContext;
            private ViewGroup mViewGroup;

            Params(Context context, ViewGroup viewGroup){
                mContext = context;
                mViewGroup = viewGroup;
            }

        }
    }
}
