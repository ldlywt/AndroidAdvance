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
import com.ldlywt.androidadvancedemo.ioc.annomation.resouces.StringById;
import com.ldlywt.androidadvancedemo.ioc.annomation.resouces.ViewById;
import com.ldlywt.androidadvancedemo.ioc.manager.InjectManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    @ViewById(R.id.tv)
    private TextView tv;
    @StringById(R.string.ioc)
    private String text;

    public static BlankFragment newInstance(){
        BlankFragment fragment = new BlankFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InjectManager.inject(this);
        tv.setText(text);
    }
}
