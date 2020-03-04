package com.ldlywt.androidadvance.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.ldlywt.androidadvance.R;
import com.ldlywt.base.view.BaseFragment;
import com.ldlywt.base.widget.loadingview.XPageStateView;

import androidx.fragment.app.Fragment;

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
    private XPageStateView mXLoadingView;

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
        mXLoadingView = XPageStateView.wrap(this);
        mXLoadingView.showLoading();
        mXLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "点击了重试按钮", Toast.LENGTH_SHORT).show();
                mXLoadingView.showNoNetwork();
            }
        });
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mXLoadingView.showError();
            }
        }, 3000);
    }

}
