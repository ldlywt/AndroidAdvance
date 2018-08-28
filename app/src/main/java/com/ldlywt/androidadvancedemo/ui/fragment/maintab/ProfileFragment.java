package com.ldlywt.androidadvancedemo.ui.fragment.maintab;

import android.os.Bundle;

import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.base.fragment.BaseFragment;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ProfileFragment extends BaseFragment{

    public static ProfileFragment newInstance(String from) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recycleview;
    }
}
