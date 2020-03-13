package com.ldlywt.hookdemo.ui;

import android.os.Bundle;
import android.view.View;

import com.ldlywt.hookdemo.databinding.FragmentTestBinding;

import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

/**
 * <pre>
 *     @author : wutao
 *     e-mail : 670831931@qq.com
 *     time   : 2020/03/13
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class TestFragment extends BaseFragment<FragmentTestBinding> {


    public TestFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    FragmentTestBinding initBinding(View view) {
        return FragmentTestBinding.bind(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.text.setText("hahahahahahahah");
    }
}
