package com.ldlywt.androidadvancedemo.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.androidadvancedemo.utils.MainTabData;
import com.ldlywt.base.view.BaseActivity;
import com.ldlywt.base.widget.CommonTextView;
import com.ldlywt.xeventbus.Subscribe;
import com.ldlywt.xeventbus.ThreadMode;
import com.ldlywt.xeventbus.XEventBus;
import com.orhanobut.logger.Logger;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/28
 *     desc   :
 *     version: 1.0
 *
 * </pre>
 */
public class MainActivity extends BaseActivity {


    private TabLayout mTabLayout;
    private Fragment[] mFragments;
    private int curIndex;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        XEventBus.getDefault().register(this);
        //自定义全局异常
//        Logger.i(1/0 + "");
        //我就测试提交
//        hookTest();
    }

    private void hookTest() {
        com.fastaac.InstrumentationProxy.replaceActivityInstrumentation(this);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.baidu.com"));
        startActivity(intent);
    }


    @Override
    public void initView() {
        mFragments = MainTabData.getFragments("Main");
        FragmentUtils.add(getSupportFragmentManager(), mFragments, R.id.fl_container, curIndex);
        mTabLayout = findViewById(R.id.bottom_tab);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showCurrentFragment(tab.getPosition());
                // Tab 选中之后，改变各个Tab的状态
                for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                    View view = mTabLayout.getTabAt(i).getCustomView();
                    ImageView icon = (ImageView) view.findViewById(R.id.tab_content_image);
                    TextView text = (TextView) view.findViewById(R.id.tab_content_text);
                    if (i == tab.getPosition()) { // 选中状态
                        icon.setImageResource(MainTabData.tabResPressed[i]);
                        text.setTextColor(getResources().getColor(android.R.color.black));
                    } else {// 未选中状态
                        icon.setImageResource(MainTabData.tabResNormal[i]);
                        text.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // 提供自定义的布局添加Tab
        for (int i = 0; i < 4; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(MainTabData.getTabView(this, i)));
        }
    }

    private void showCurrentFragment(int index) {
        FragmentUtils.showHide(curIndex = index, mFragments);
    }

    @Override
    protected void initTitle() {
//        XToolBar toolBar = (XToolBar) new XToolBar
//                .Builder(this)
//                .builder();
//        toolBar.getTitle().setLeftTextString("标题");
        super.initTitle();
        getTitleBar()
                .setWidthAndHeight(LinearLayout.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(40))
                .setBackColor(R.color.colorPrimary)
                .setCenterTextColor(R.color.white)
                .setCenterTextString("自定义标题")
                .setRightTextString("添加")
                .setRightTextColor(R.color.colorAccent)
                .setRightViewIsClickable(true)
                .setOnCommonTextViewClickListener(new CommonTextView.OnCommonTextViewClickListener() {
                    @Override
                    public void onRightViewClick() {
                        startActivity(new Intent(MainActivity.this, TestActivity.class));
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        XEventBus.getDefault().unRegister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverMsg(String msg) {
        Logger.i("来自自定义的EventBus： " + msg);
    }
}
