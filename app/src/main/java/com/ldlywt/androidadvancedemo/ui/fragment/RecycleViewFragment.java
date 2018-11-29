package com.ldlywt.androidadvancedemo.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.androidadvancedemo.view.decoration.LineDecoration;
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
public class RecycleViewFragment extends BaseFragment {

    private ArrayList<String> mList;
    private RecyclerView mRecyclerView;
    private InnerAdapter mAdapter;

    public static Fragment newInstance(String from) {
        RecycleViewFragment fragment = new RecycleViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.recycleview;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mList.add("我是第" + i + "条数据");
        }
        mAdapter.setNewData(mList);
    }

    @Override
    public void initView() {
        mRecyclerView = getView().findViewById(R.id.recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new InnerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new LineDecoration(getContext(), 20, Color.RED));
    }

    class InnerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public InnerAdapter() {
            super(R.layout.item_text);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv, item);
        }
    }

}
