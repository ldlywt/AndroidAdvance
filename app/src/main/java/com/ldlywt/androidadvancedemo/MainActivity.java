package com.ldlywt.androidadvancedemo;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ldlywt.androidadvancedemo.aspect.CheckNetwork;
import com.ldlywt.androidadvancedemo.aspect.CheckPermission;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                checkPermission();
                break;
            case R.id.btn2:
                checkNetwork();
                break;
        }
    }


    @CheckPermission(value = Manifest.permission.CAMERA)
    public void checkPermission(){
        Log.i(TAG,"检查权限");
    }

    @CheckNetwork()
    public void checkNetwork(){
        Log.i(TAG,"检查手机是否有网");
    }
}
