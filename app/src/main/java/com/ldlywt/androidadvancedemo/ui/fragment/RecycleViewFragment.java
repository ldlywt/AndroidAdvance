package com.ldlywt.androidadvancedemo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.androidadvancedemo.view.decoration.TimelineItemDecoration;
import com.ldlywt.base.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

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

    private RecyclerView mRecyclerView;
    private InnerAdapter mAdapter;
    private TimelineItemDecoration mDecor;

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
        mAdapter.setNewData(getMockData());
        mDecor.setDataList(getMockData());
    }

    public List<TimelineItemDecoration.Units> getMockData() {
        List<TimelineItemDecoration.Units> list = new ArrayList<>();
        list.add(new TimelineItemDecoration.Units("2019年1月1日", "9:00"));
        list.add(new TimelineItemDecoration.Units("2019年1月1日", "15:00"));
        list.add(new TimelineItemDecoration.Units("2019年1月1日", "17:00"));
        list.add(new TimelineItemDecoration.Units("2019年1月2日", "9:00"));
        list.add(new TimelineItemDecoration.Units("2019年1月3日", "12:00"));
        list.add(new TimelineItemDecoration.Units("2019年1月5日", "19:00"));
        list.add(new TimelineItemDecoration.Units("2019年1月5日", "19:00"));
        list.add(new TimelineItemDecoration.Units("2019年1月6日", "11:00"));
        list.add(new TimelineItemDecoration.Units("2019年1月7日", "13:00"));
        list.add(new TimelineItemDecoration.Units("2019年1月21日", "7:00"));
        list.add(new TimelineItemDecoration.Units("2019年1月21日", "8:00"));
        list.add(new TimelineItemDecoration.Units("2019年1月21日", "9:00"));
        list.add(new TimelineItemDecoration.Units("2019年1月25日", "7:00"));
        list.add(new TimelineItemDecoration.Units("2019年1月25日", "17:00"));
        list.add(new TimelineItemDecoration.Units("2019年1月25日", "20:00"));
        return list;
    }

    @Override
    public void initView() {
        mRecyclerView = getView().findViewById(R.id.recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mAdapter = new InnerAdapter();
        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.addItemDecoration(new LineDecoration(getContext()));
//        mRecyclerView.addItemDecoration(new GridDecoration(getContext()));
//        SpaceDecoration decor = new SpaceDecoration(10);
//        decor.setPaddingStart(false);
//        decor.setPaddingEdgeSide(false);
        mDecor = new TimelineItemDecoration(getActivity());
        mRecyclerView.addItemDecoration(mDecor);
    }

    class InnerAdapter extends BaseItemDraggableAdapter<TimelineItemDecoration.Units, BaseViewHolder> {

        public InnerAdapter() {
            super(R.layout.item_text, null);
        }

        @Override
        protected void convert(BaseViewHolder helper, TimelineItemDecoration.Units item) {
            helper.setText(R.id.tv, item.date + "  " + item.time);
        }
    }

}
