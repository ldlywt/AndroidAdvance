package com.ldlywt.base.widget.loadingview;


import com.ldlywt.base.R;

import androidx.annotation.LayoutRes;

public class XPageStateConfig {

    private int emptyViewResId = R.layout.xpage_state_empty_view;
    private int errorViewResId = R.layout.xpage_state_error_view;
    private int loadingViewResId = R.layout.xpage_state_loading_view;
    private int noNetworkViewResId = R.layout.xpage_state_no_network_view;

    public int getEmptyViewResId() {
        return emptyViewResId;
    }

    public XPageStateConfig setEmptyViewResId(@LayoutRes int emptyViewResId) {
        this.emptyViewResId = emptyViewResId;
        return this;
    }

    public int getErrorViewResId() {
        return errorViewResId;
    }

    public XPageStateConfig setErrorViewResId(@LayoutRes int errorViewResId) {
        this.errorViewResId = errorViewResId;
        return this;
    }
    public int getLoadingViewResId() {
        return loadingViewResId;
    }

    public XPageStateConfig setLoadingViewResId(@LayoutRes int loadingViewResId) {
        this.loadingViewResId = loadingViewResId;
        return this;
    }

    public int getNoNetworkViewResId() {
        return noNetworkViewResId;
    }

    public XPageStateConfig setNoNetworkViewResId(@LayoutRes int noNetworkViewResId) {
        this.noNetworkViewResId = noNetworkViewResId;
        return this;
    }
}
