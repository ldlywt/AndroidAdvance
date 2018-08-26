package com.ldlywt.androidadvancedemo.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.androidadvancedemo.ui.fragment.AspectFragment;
import com.ldlywt.androidadvancedemo.ui.fragment.DialogTestFragment;
import com.ldlywt.androidadvancedemo.ui.fragment.HttpFragment;
import com.ldlywt.androidadvancedemo.ui.fragment.ImagesFragment;
import com.ldlywt.base.activity.BaseActivity;
import com.ldlywt.ioc.annomation.event.OnClick;
import com.ldlywt.ioc.annomation.event.OnLongClick;
import com.ldlywt.ioc.annomation.network.CheckNet;
import com.ldlywt.ioc.annomation.resouces.ContentViewById;
import com.ldlywt.ioc.manager.InjectManager;
import com.ldlywt.xtoolbar.XToolBar;

@ContentViewById(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    protected void initFirst() {
        InjectManager.inject(this);
    }

    @Override
    protected void initTitle() {
        new XToolBar
                .Builder(this, findViewById(R.id.ll_root))
                .builder();
    }

    //支持数组形式的绑定，绑定多个控件
    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5})
    @OnLongClick({R.id.btn5})
    @CheckNet()
    public void openIoc(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startContainerActivity(AspectFragment.class.getCanonicalName());
                break;
            case R.id.btn2:
//                startActivity(new Intent(this, ImageActivity.class));
                startContainerActivity(ImagesFragment.class.getCanonicalName());
                break;
            case R.id.btn3:
                startContainerActivity(HttpFragment.class.getCanonicalName());
                break;
            case R.id.btn4:
                startContainerActivity(DialogTestFragment.class.getCanonicalName());
                break;
            case R.id.btn5:
                Toast.makeText(this, "IOC长按", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
