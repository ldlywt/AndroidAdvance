package com.ldlywt.androidadvancedemo.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.ioc.annomation.resouces.ColorById;
import com.ldlywt.ioc.annomation.resouces.StringById;
import com.ldlywt.ioc.annomation.resouces.ViewById;
import com.ldlywt.ioc.manager.InjectManager;


/**
 * 测试下IOC框架在fragment中能不能使用
 */
public class IocFragment extends Fragment {

    @ViewById(R.id.tv)
    private TextView tv;
    @StringById(R.string.ioc)
    private String text;

    @ColorById(R.color.colorAccent)
    private int color;

    public static IocFragment newInstance(){
        return new IocFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ioc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InjectManager.inject(this);
        tv.setText(text);
        tv.setBackgroundColor(color);
    }
}
