package com.ldlywt.androidadvance.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ldlywt.androidadvance.R;
import com.ldlywt.base.view.BaseFragment;
import com.ldlywt.views.ChartView;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/28
 *     desc   : 可以左右滑动的双折线图
 *     version: 1.0
 * </pre>
 */
public class ChartFragment extends BaseFragment implements View.OnClickListener {

    private ArrayList<ChartView.Units> mUnitList1 = new ArrayList();
    private ArrayList<ChartView.Units> mUnitList2 = new ArrayList();
    private ChartView mChartView;

    public static Fragment newInstance(String from) {
        ChartFragment fragment = new ChartFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_chart;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        for (int i = 0; i < 40; i++) {
            mUnitList1.add(new ChartView.Units(String.valueOf(i + ":00"), (float) (Math.random() * 181 + 60)));
            mUnitList2.add(new ChartView.Units(String.valueOf(i + ":00"), (float) (Math.random() * 181 + 60)));
        }

        mChartView.setValue(mUnitList1, mUnitList2);
        mChartView.setYLimit(240, 4);
        mChartView.setSelectIndex(3);
        mChartView.setChartLineValueListener(new ChartView.OnChartLineValueListener() {
            @Override
            public void onChartValue(float lineOneValue, float lineTweValue) {
                Log.i("page", "onChartValue: " + lineOneValue + " / " + lineTweValue);
            }
        });
    }


    @Override
    public void initView() {
        mChartView = (ChartView) getView().findViewById(R.id.chartView);
        getView().findViewById(R.id.bt_pre).setOnClickListener(this);
        getView().findViewById(R.id.bt_next).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_pre:
                mChartView.slide(100, 200, false);
                break;
            case R.id.bt_next:
                mChartView.slide(100, 200, true);
                break;
        }
    }
}
