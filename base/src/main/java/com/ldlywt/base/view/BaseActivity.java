package com.ldlywt.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;

import com.ldlywt.base.R;
import com.ldlywt.base.widget.CommonTextView;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity implements ICallback{

    protected BaseActivity mActivity = BaseActivity.this;
    private CommonTextView mTitle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
        }
        initTitle();
        initView();
        initData(savedInstanceState);
    }

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
