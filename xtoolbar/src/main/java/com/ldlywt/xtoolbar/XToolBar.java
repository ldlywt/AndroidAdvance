package com.ldlywt.xtoolbar;

import android.content.Context;
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
public class XToolBar extends AbsToolBar {

    private XToolBar(Builder.Params params) {
        super(params);
    }

    @Override
    public int getLayoutRes() {
        return 0;
    }

    @Override
    public void apply() {

    }

    public static class Builder extends AbsToolBar.Builder{

        XParams p;

        public Builder(Context context) {
            this(context,null);
        }

        public Builder(Context context, ViewGroup viewGroup) {
            p = new XParams(context,viewGroup);
        }

        @Override
        public AbsToolBar builder() {
            XToolBar xToolBar = new XToolBar(p);


            return xToolBar;
        }

        public static class XParams extends AbsToolBar.Builder.Params{

            XParams(Context context, ViewGroup viewGroup) {
                super(context, viewGroup);
            }
        }
    }


}
