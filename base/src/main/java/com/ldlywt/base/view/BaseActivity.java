package com.ldlywt.base.view;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.ldlywt.base.R;
import com.ldlywt.base.widget.CommonTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewbinding.ViewBinding;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity{

    protected BaseActivity mActivity = BaseActivity.this;
    private CommonTextView mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        initTitle();
        initView();
        initData(savedInstanceState);
    }

    //返回布局文件id
    public abstract void initView();
    //初始化数据
    public abstract void initData(Bundle savedInstanceState);

    protected void hideActionBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    protected void initTitle() {
        mTitle = new CommonTextView(this);
        mTitle
                .setLeftDrawableLeft(ContextCompat.getDrawable(this, R.drawable.ic_back))
                .setLeftTextString(getResources().getString(R.string.back))
                .setLeftTextColor(R.color.white)
                .setLeftViewIsClickable(true)
                .setOnCommonTextViewClickListener(new CommonTextView.OnCommonTextViewClickListener() {
                    @Override
                    public void onLeftViewClick() {
                        BaseActivity.super.onBackPressed();
                    }
                })
        ;
        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        ViewGroup viewGroup = (ViewGroup) decorView.getChildAt(0);
        viewGroup.addView(mTitle, 0);
    }

    public CommonTextView getTitleBar() {
        return mTitle;
    }

}
