package com.ldlywt.androidadvancedemo.ui.fragment.maintab;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.base.view.BaseFragment;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class AttentionFragment extends BaseFragment{

    public static AttentionFragment newInstance(String from) {
        AttentionFragment fragment = new AttentionFragment();
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
        getView().setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorAccent));
    }
}
