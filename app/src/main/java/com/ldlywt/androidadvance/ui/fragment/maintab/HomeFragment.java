package com.ldlywt.androidadvance.ui.fragment.maintab;

import android.os.Bundle;
import android.view.View;

import com.ldlywt.androidadvance.R;
import com.ldlywt.androidadvance.ui.fragment.AspectFragment;
import com.ldlywt.androidadvance.ui.fragment.ChartFragment;
import com.ldlywt.androidadvance.ui.fragment.DialogTestFragment;
import com.ldlywt.androidadvance.ui.fragment.HttpFragment;
import com.ldlywt.androidadvance.ui.fragment.ImagesFragment;
import com.ldlywt.androidadvance.ui.fragment.LoadViewFragment;
import com.ldlywt.androidadvance.ui.fragment.RecycleViewFragment;
import com.ldlywt.androidadvance.ui.fragment.RvBannerFragment;
import com.ldlywt.androidadvance.utils.ContainerActivityUtils;
import com.ldlywt.base.view.BaseFragment;
import com.ldlywt.ioc.annomation.event.OnClick;
import com.ldlywt.ioc.annomation.network.CheckNet;
import com.ldlywt.ioc.manager.InjectManager;

import androidx.fragment.app.Fragment;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HomeFragment extends BaseFragment {

    public static Fragment newInstance(String from) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        InjectManager.inject(this);
    }

    @Override
    public void initView() {

    }

    //支持数组形式的绑定，绑定多个控件
    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8})
//    @OnLongClick({R.id.btn5})
    @CheckNet()
    public void openIoc(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                ContainerActivityUtils.startContainerActivity(getActivity(), AspectFragment.class.getCanonicalName());
                break;
            case R.id.btn2:
                ContainerActivityUtils.startContainerActivity(getActivity(),ImagesFragment.class.getCanonicalName());
                break;
            case R.id.btn3:
                ContainerActivityUtils.startContainerActivity(getActivity(),HttpFragment.class.getCanonicalName());
                break;
            case R.id.btn4:
                ContainerActivityUtils.startContainerActivity(getActivity(),DialogTestFragment.class.getCanonicalName());
                break;
            case R.id.btn5:
                ContainerActivityUtils.startContainerActivity(getActivity(), LoadViewFragment.class.getCanonicalName());
                break;
            case R.id.btn6:
                ContainerActivityUtils.startContainerActivity(getActivity(), RecycleViewFragment.class.getCanonicalName());
                break;
            case R.id.btn7:
                ContainerActivityUtils.startContainerActivity(getActivity(), RvBannerFragment.class.getCanonicalName());
                break;
            case R.id.btn8:
                ContainerActivityUtils.startContainerActivity(getActivity(), ChartFragment.class.getCanonicalName());
                break;
        }
    }
}
