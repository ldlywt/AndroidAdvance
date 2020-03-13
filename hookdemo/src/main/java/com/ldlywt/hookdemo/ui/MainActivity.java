package com.ldlywt.hookdemo.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ldlywt.hookdemo.R;
import com.ldlywt.hookdemo.databinding.ActivityMainBinding;

import java.lang.reflect.Method;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bt.setText("启动插件");

        getSupportFragmentManager().beginTransaction().add(R.id.fl_test,new TestFragment()).commit();

        findViewById(R.id.bt).setOnClickListener(v -> {
            Toast.makeText(this, "启动插件", Toast.LENGTH_SHORT).show();
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
        });
    }
}
