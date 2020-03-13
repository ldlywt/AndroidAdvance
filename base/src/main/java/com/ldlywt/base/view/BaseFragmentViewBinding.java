package com.ldlywt.base.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

/**
 * <pre>
 *     author : lex
 *     desc   : ViewBinding 封装 Android Studio 3.6+
 *     time   : 2020.3.13
 *     version: 1.0
 * </pre>
 */
public abstract class BaseFragmentViewBinding<T extends ViewBinding> extends Fragment {

    public BaseFragmentViewBinding(int contentLayoutId) {
        super(contentLayoutId);
    }

    protected AppCompatActivity mActivity;
    protected T mBinding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;//保存Context引用
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding = initBinding(view);
    }

    abstract T initBinding(View view);

    @Override
    public void onDestroyView() {
        mBinding = null;
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public boolean onBackPressed() {
        return false;
    }
}
