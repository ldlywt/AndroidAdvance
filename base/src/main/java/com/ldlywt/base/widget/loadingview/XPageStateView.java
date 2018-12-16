package com.ldlywt.base.widget.loadingview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ldlywt.base.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 简单实用的页面状态统一管理 ，加载中、无网络、无数据、出错等状态的随意切换
 */
public class XPageStateView extends FrameLayout {

    public static XPageStateConfig config = new XPageStateConfig();
    private int mEmptyViewResId;
    private int mErrorViewResId;
    private int mLoadingViewResId;
    private int mNoNetworkViewResId;
    private int mContentViewResId;
    private LayoutInflater mInflater;
    private OnClickListener mOnRetryClickListener;
    private Map<Integer, View> mResId = new HashMap<>();

    public XPageStateView(Context context) {
        this(context, null);
    }

    public XPageStateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XPageStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.XPageStateView, defStyleAttr, 0);
        mEmptyViewResId = a.getResourceId(R.styleable.XPageStateView_emptyView, config.getEmptyViewResId());
        mErrorViewResId = a.getResourceId(R.styleable.XPageStateView_errorView, config.getErrorViewResId());
        mLoadingViewResId = a.getResourceId(R.styleable.XPageStateView_loadingView, config.getLoadingViewResId());
        mNoNetworkViewResId = a.getResourceId(R.styleable.XPageStateView_noNetworkView, config.getNoNetworkViewResId());
        a.recycle();
    }

    public static XPageStateConfig init() {
        return config;
    }

    public static XPageStateView wrap(Activity activity) {
        return wrap(((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0));
    }

    public static XPageStateView wrap(View view) {
        if (view == null) {
            throw new RuntimeException("content view can not be null");
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (view == null) {
            throw new RuntimeException("parent view can not be null");
        }
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        int index = parent.indexOfChild(view);
        parent.removeView(view);

        XPageStateView xLoadingView = new XPageStateView(view.getContext());
        parent.addView(xLoadingView, index, lp);
        xLoadingView.addView(view);
        xLoadingView.setContentView(view);
        return xLoadingView;
    }

    private void setContentView(View view) {
        mContentViewResId = view.getId();
        mResId.put(mContentViewResId, view);
    }

    public static XPageStateView wrap(Fragment fragment) {
        return wrap(fragment.getView());
    }

    public final void showEmpty() {
        show(mEmptyViewResId);
    }

    private void show(int resId) {
        for (View view : mResId.values()) {
            view.setVisibility(GONE);
        }
        layout(resId).setVisibility(VISIBLE);
    }

    private View layout(int resId) {
        if (mResId.containsKey(resId)) {
            return mResId.get(resId);
        }
        View view = mInflater.inflate(resId, this, false);
        view.setVisibility(GONE);
        addView(view);
        mResId.put(resId, view);
        if (resId == mErrorViewResId || resId == mNoNetworkViewResId) {
            View v = view.findViewById(R.id.xloading_retry);
            if (mOnRetryClickListener != null) {
                if (v != null)
                    v.setOnClickListener(mOnRetryClickListener);
                else
                    view.setOnClickListener(mOnRetryClickListener);
            }
        }
        return view;
    }

    public final void showError() {
        show(mErrorViewResId);
    }

    public final void showNoNetwork() {
        show(mNoNetworkViewResId);
    }

    public final void showContent() {
        show(mContentViewResId);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i("wutao", "onFinishInflate: onFinishInflate");
        if (getChildCount() == 0) {
            return;
        }
        if (getChildCount() > 1) {
            removeViews(1, getChildCount() - 1);
        }
        View view = getChildAt(0);
        setContentView(view);
        showLoading();
    }

    public final void showLoading() {
        show(mLoadingViewResId);
    }

    /**
     * 设置重试点击事件
     *
     * @param onRetryClickListener 重试点击事件
     */
    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.mOnRetryClickListener = onRetryClickListener;
    }
}