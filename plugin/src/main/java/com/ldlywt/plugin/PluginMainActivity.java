package com.ldlywt.plugin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class PluginMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        Log.i("wutao", "onCreate: 我是插件的Activity");
    }
}
