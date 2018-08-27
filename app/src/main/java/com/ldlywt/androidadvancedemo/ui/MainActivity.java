package com.ldlywt.androidadvancedemo.ui;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.androidadvancedemo.ui.fragment.AspectFragment;
import com.ldlywt.androidadvancedemo.ui.fragment.DialogTestFragment;
import com.ldlywt.androidadvancedemo.ui.fragment.HttpFragment;
import com.ldlywt.androidadvancedemo.ui.fragment.ImagesFragment;
import com.ldlywt.base.activity.BaseActivity;
import com.ldlywt.base.utils.DensityUtil;
import com.ldlywt.base.view.CommonTextView;
import com.ldlywt.ioc.annomation.event.OnClick;
import com.ldlywt.ioc.annomation.event.OnLongClick;
import com.ldlywt.ioc.annomation.network.CheckNet;
import com.ldlywt.ioc.annomation.resouces.ContentViewById;
import com.ldlywt.ioc.manager.InjectManager;

@ContentViewById(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    protected void initFirst() {
        InjectManager.inject(this);
    }

    @Override
    protected void initTitle() {
//        XToolBar toolBar = (XToolBar) new XToolBar
//                .Builder(this)
//                .builder();
//        toolBar.getTitle().setLeftTextString("标题");
        super.initTitle();
        getTitleBar()
                .setWidthAndHeight(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this,40))
                .setBackColor(R.color.colorPrimary)
                .setCenterTextColor(R.color.white)
                .setCenterTextString("自定义标题")
                .setRightTextString("添加")
                .setRightTextColor(R.color.colorAccent)
                .setRightViewIsClickable(true)
                .setOnCommonTextViewClickListener(new CommonTextView.OnCommonTextViewClickListener(){
                    @Override
                    public void onRightViewClick() {
                        Toast.makeText(mActivity, "点击了添加", Toast.LENGTH_SHORT).show();
                    }
                });
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
