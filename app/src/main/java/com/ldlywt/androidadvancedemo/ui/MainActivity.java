package com.ldlywt.androidadvancedemo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.ioc.annomation.event.OnClick;
import com.ldlywt.ioc.annomation.event.OnLongClick;
import com.ldlywt.ioc.annomation.network.CheckNet;
import com.ldlywt.ioc.annomation.resouces.ColorById;
import com.ldlywt.ioc.annomation.resouces.ContentViewById;
import com.ldlywt.ioc.annomation.resouces.StringById;
import com.ldlywt.ioc.annomation.resouces.ViewById;
import com.ldlywt.ioc.manager.InjectManager;

@ContentViewById(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.btn1)
    private Button btn1;
    @ViewById(R.id.tv_ioc)
    private TextView tvIoc;

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
        btn1.setText("AOP切面编程");
    }

    //支持数组形式的绑定，绑定多个控件
    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,R.id.btn5})
    @OnLongClick({R.id.btn5})
    @CheckNet()
    public void openIoc(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startActivity(new Intent(this, AspectActivity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this, IocActivity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(this, ImageActivity.class));
                break;
            case R.id.btn4:
                startActivity(new Intent(this, HttpActivity.class));
                break;
            case R.id.btn5:
                Toast.makeText(this, "IOC长按", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
