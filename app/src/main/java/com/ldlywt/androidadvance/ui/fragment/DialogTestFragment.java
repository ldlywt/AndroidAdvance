package com.ldlywt.androidadvance.ui.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ldlywt.androidadvance.R;
import com.ldlywt.androidadvance.bean.TestBean;
import com.ldlywt.base.view.BaseFragment;
import com.ldlywt.xdialog.XDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *     author : wutao
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class DialogTestFragment extends BaseFragment {

    private static final String TAG = DialogTestFragment.class.getSimpleName();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dialog;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        getView().findViewById(R.id.btn_dialog).setOnClickListener(view1 -> showDialog());
        getView().findViewById(R.id.btn_list).setOnClickListener(view1 -> showListDialog());
    }

    private void showListDialog() {
        RecyclerView recycleView = (RecyclerView) LayoutInflater.from(mActivity).inflate(R.layout.recycleview, null);
        recycleView.setLayoutManager(new LinearLayoutManager(mActivity));
        ArrayList<TestBean> list = new ArrayList<>();
        list.add(new TestBean("小王",1));
        list.add(new TestBean("小李",2));
        list.add(new TestBean("小张",3));
        MyAdapter adapter = new MyAdapter(R.layout.item_text, list);
        recycleView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) ->
                Toast.makeText(mActivity, "点击了条目---> " + position, Toast.LENGTH_SHORT).show());
        new XDialog.Builder(mActivity)
                .setView(recycleView)
                .setDimAmount(0.6f)     //设置弹窗背景透明度(0-1f)
                .create()
                .show();
    }

    private void showDialog() {
        //弹窗隐藏时回调方法
        //View控件点击事件回调
        new XDialog.Builder(mActivity)
                .setLayoutRes(R.layout.dialog_normal)    //设置弹窗展示的xml布局
//                .setView(view)  //设置弹窗布局,直接传入View
                .setWidth(600)  //设置弹窗宽度(px)
//                .setHeight(800)  //设置弹窗高度(px)
                .setScreenWidthAspect(mActivity, 0.8f)   //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
//                .setScreenHeightAspect(mActivity, 0.3f)  //设置弹窗高度(参数aspect为屏幕宽度比例 0 - 1f)
                .setGravity(Gravity.CENTER)     //设置弹窗展示位置
                .setTag(TAG)   //设置Tag
                .setDimAmount(0.6f)     //设置弹窗背景透明度(0-1f)
                .setCancelableOutside(true)     //弹窗在界面外是否可以点击取消
                .setDialogAnimationRes(R.style.animate_dialog_scale) //设置弹窗动画
                .setOnDismissListener(dialog -> Toast.makeText(mActivity, "弹窗消失回调", Toast.LENGTH_SHORT).show())
                .addOnClickListener(R.id.btn_left, R.id.btn_right, R.id.tv_title)   //添加进行点击控件的id
                .setOnViewClickListener((view, tDialog) -> {
                    switch (view.getId()) {
                        case R.id.btn_left:
                            Toast.makeText(mActivity, "点击确定", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.btn_right:
                            Toast.makeText(mActivity, "点击了取消", Toast.LENGTH_SHORT).show();
                            tDialog.dismiss();
                            break;
                        case R.id.tv_title:
                            Toast.makeText(mActivity, "我是标题，点我干嘛", Toast.LENGTH_SHORT).show();
                            break;
                    }
                })
                .create()
                .show();
    }

    private class MyAdapter extends BaseQuickAdapter<TestBean,BaseViewHolder>{

        MyAdapter(int layoutResId, @Nullable List<TestBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, TestBean item) {
            helper.setText(R.id.tv,item.getName());
        }
    }

}
