package com.ldlywt.androidadvancedemo.ui.fragment.maintab;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.androidadvancedemo.bean.PieData;
import com.ldlywt.androidadvancedemo.view.PieView;
import com.ldlywt.base.view.BaseFragment;

import java.util.ArrayList;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class DiscoveryFragment extends BaseFragment implements View.OnClickListener {


    private PieView mPieView;

    public static DiscoveryFragment newInstance(String from) {
        DiscoveryFragment fragment = new DiscoveryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_discovery;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ArrayList<PieData> list = new ArrayList<>();
        list.add(new PieData("1", 20));
        list.add(new PieData("2", 10));
        list.add(new PieData("3", 50));
        list.add(new PieData("4", 40));
        list.add(new PieData("5", 35));
        mPieView.setData(list);
        mPieView.setStartAngle(0);
    }

    @Override
    public void initView() {
        mPieView = getView().findViewById(R.id.pie);
        mPieView.setOnPieListener(new PieView.PieListener() {
            @Override
            public void onItemClick(float value) {
                ToastUtils.showShort("点击的value： " + value);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
