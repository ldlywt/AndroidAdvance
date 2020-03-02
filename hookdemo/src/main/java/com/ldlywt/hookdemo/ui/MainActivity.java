package com.ldlywt.hookdemo.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ldlywt.hookdemo.R;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Class<?> clazz = Class.forName("com.ldlywt.plugin.Test");
                    Method method = clazz.getMethod("print");
                    method.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.ldlywt.plugin",
                        "com.ldlywt.plugin.PluginMainActivity"));
                startActivity(intent);
            }
        });
    }
}
