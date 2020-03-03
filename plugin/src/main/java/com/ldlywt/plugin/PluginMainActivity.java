package com.ldlywt.plugin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


public class PluginMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        Log.i("wutao", "onCreate: 我是插件的Activity");
        Toast.makeText(this, "onCreate: 我是插件的Activity", Toast.LENGTH_SHORT).show();
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_main, null);
        setContentView(view);
    }

}
