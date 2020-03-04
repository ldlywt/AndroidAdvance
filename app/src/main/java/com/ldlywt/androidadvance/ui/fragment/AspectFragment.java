package com.ldlywt.androidadvance.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.NetworkUtils;
import com.ldlywt.androidadvance.R;
import com.ldlywt.base.view.BaseFragment;
import com.ldlywt.ioc.annomation.resouces.ColorById;
import com.ldlywt.ioc.annomation.resouces.StringById;
import com.ldlywt.ioc.annomation.resouces.ViewById;
import com.ldlywt.ioc.manager.InjectManager;

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
    public int getLayoutId() {
        return R.layout.fragment_aspect;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //必须要写在这里，要等view初始化完才能注入
        InjectManager.inject(this);
        btn1.setText(text);
        btn1.setTextColor(color);
        getView().findViewById(R.id.btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.showShort("点击了按钮");
            }
        });
    }

    @Override
    public void initView() {

    }



    public void checkNetworkNormal() {
        if (NetworkUtils.isConnected()) {
            Log.i(TAG, "手机有网，可以进入下一个页面");
        } else {
            Log.i(TAG, "请检查手机网络设置");
        }
    }
}
