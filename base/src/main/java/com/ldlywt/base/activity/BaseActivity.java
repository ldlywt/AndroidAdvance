package com.ldlywt.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ldlywt.base.R;
import com.ldlywt.base.utils.DensityUtil;
import com.ldlywt.base.view.CommonTextView;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity {

    private CommonTextView mTitle;

    protected BaseActivity mActivity = BaseActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        initFirst();
        initTitle();
        initData();
    }

    protected void initData() {

    }

    protected void hideActionBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    protected void initFirst() {

    }

    protected void initTitle() {
        mTitle = new CommonTextView(this);
        mTitle
                .setLeftDrawableLeft(ContextCompat.getDrawable(this,R.drawable.ic_back))
                .setLeftTextString(getResources().getString(R.string.back))
                .setLeftTextColor(R.color.white)
                .setLeftViewIsClickable(true)
                .setOnCommonTextViewClickListener(new CommonTextView.OnCommonTextViewClickListener(){
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

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    public void startContainerActivity(String canonicalName) {
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        startActivity(intent);
    }
}
