package com.ldlywt.androidadvancedemo.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.androidadvancedemo.utils.ContainerActivityUtils;
import com.ldlywt.base.view.BaseFragment;
import com.ldlywt.base.widget.loadingview.XLoadingView;
import com.ldlywt.ioc.annomation.event.OnClick;
import com.ldlywt.ioc.annomation.network.CheckNet;
import com.ldlywt.ioc.manager.InjectManager;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LoadViewFragment extends BaseFragment {

    private Handler mHandler = new Handler();
    private XLoadingView mXLoadingView;

    public static Fragment newInstance(String from) {
        LoadViewFragment fragment = new LoadViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.recycleview;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        mXLoadingView = XLoadingView.wrap(this);
        mXLoadingView.showLoading();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mXLoadingView.showEmpty();
            }
        }, 3000);
    }

}
