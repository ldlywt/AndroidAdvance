package com.ldlywt.androidadvancedemo.ui.fragment;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.NetworkUtils;
import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.androidadvancedemo.aspect.BehaviorTrace;
import com.ldlywt.androidadvancedemo.aspect.CheckLogin;
import com.ldlywt.androidadvancedemo.aspect.CheckNetwork;
import com.ldlywt.androidadvancedemo.aspect.CheckPermission;
import com.ldlywt.base.view.BaseFragment;
import com.ldlywt.ioc.annomation.event.OnClick;
import com.ldlywt.ioc.annomation.resouces.ColorById;
import com.ldlywt.ioc.annomation.resouces.StringById;
import com.ldlywt.ioc.annomation.resouces.ViewById;
import com.ldlywt.ioc.manager.InjectManager;
import com.ldlywt.xeventbus.XEventBus;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class AspectFragment extends BaseFragment {

    public static final String TAG = AspectFragment.class.getSimpleName();

    @StringById(R.string.ioc)
    private String text;

    @ColorById(R.color.colorAccent)
    private int color;

    @ViewById(R.id.btn1)
    private Button btn1;

    @Override
    public void initData(Bundle savedInstanceState) {
        //必须要写在这里，要等view初始化完才能注入
        InjectManager.inject(this);
        btn1.setText(text);
        btn1.setTextColor(color);
    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_aspect;
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,R.id.btn5})
    public void click(View view){
        switch (view.getId()) {
            case R.id.btn1:
                checkPermission();
                break;
            case R.id.btn2:
                checkNetwork();
                break;
            case R.id.btn3:
                checkLogin();
                break;
            case R.id.btn4:
                behaviorTrace();
                break;
            case R.id.btn5:
                XEventBus.getDefault().post("EventBus发送消息");
                break;
        }
    }

    @BehaviorTrace(value = "打开首页", type = 1)
    public void behaviorTrace(){
        Log.i(TAG,"用户行为检查");
    }

    @CheckPermission(value = Manifest.permission.CAMERA)
    public void checkPermission(){
        Log.i(TAG,"检查权限");
    }

    @CheckLogin()
    public void checkLogin(){
        Log.i(TAG,"检查用户是否登录");
    }

    @CheckNetwork()
    public void checkNetwork(){
        Log.i(TAG,"进入下一个Activity");
    }


    public void checkNetworkNormal(){
        if (NetworkUtils.isConnected()) {
            Log.i(TAG, "手机有网，可以进入下一个页面");
        } else {
            Log.i(TAG,"请检查手机网络设置");
        }
    }
}
