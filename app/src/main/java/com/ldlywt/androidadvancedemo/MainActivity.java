package com.ldlywt.androidadvancedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ldlywt.androidadvancedemo.ioc.annomation.event.OnClick;
import com.ldlywt.androidadvancedemo.ioc.annomation.event.OnLongClick;
import com.ldlywt.androidadvancedemo.ioc.annomation.network.CheckNet;
import com.ldlywt.androidadvancedemo.ioc.annomation.resouces.ColorById;
import com.ldlywt.androidadvancedemo.ioc.annomation.resouces.ContentViewById;
import com.ldlywt.androidadvancedemo.ioc.annomation.resouces.StringById;
import com.ldlywt.androidadvancedemo.ioc.annomation.resouces.ViewById;
import com.ldlywt.androidadvancedemo.ioc.manager.InjectManager;

@ContentViewById(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    @ViewById(R.id.btn1)
    private Button btn1;
    @ViewById(R.id.btn2)
    private Button btn2;
    @ViewById(R.id.btn3)
    private Button btn3;
    @ViewById(R.id.btn4)
    private Button btn4;
    @ViewById(R.id.btn5)
    private Button btn5;
    @ViewById(R.id.tv_ioc)
    private TextView tvIoc;

    @StringById(R.string.ioc)
    private String text;
    @ColorById(R.color.colorAccent)
    private int color;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //IOC注入
        InjectManager.inject(this);
//        setContentView(R.layout.activity_main);
//        findViewById(R.id.btn1).setOnClickListener(this);
//        findViewById(R.id.btn2).setOnClickListener(this);
//        findViewById(R.id.btn3).setOnClickListener(this);
//        findViewById(R.id.btn4).setOnClickListener(this);
//        findViewById(R.id.btn5).setOnClickListener(this);
        tvIoc.setText(text);
        tvIoc.setBackgroundColor(color);
    }

    //支持数组形式的绑定，绑定多个控件
    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn5})
    @OnLongClick({R.id.btn4})
    @CheckNet()
    public void openIoc(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startActivity(new Intent(this, AspectActivity.class));
                break;
            case R.id.btn2:
                Toast.makeText(this, "点击了按钮222", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn3:
                Toast.makeText(this, "点击了按钮3333", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn4:
                Toast.makeText(this, "长按点击了按----------", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn5:

                break;
        }
    }

}
