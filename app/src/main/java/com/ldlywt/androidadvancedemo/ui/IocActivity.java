package com.ldlywt.androidadvancedemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ldlywt.androidadvancedemo.ui.fragment.IocFragment;
import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.ioc.annomation.resouces.ContentViewById;
import com.ldlywt.ioc.manager.InjectManager;

@ContentViewById(R.layout.activity_ioc)
public class IocActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.inject(this);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl, IocFragment.newInstance())
                .commit();
    }
}
