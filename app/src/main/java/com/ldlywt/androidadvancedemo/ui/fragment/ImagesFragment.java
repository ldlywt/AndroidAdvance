package com.ldlywt.androidadvancedemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.androidadvancedemo.bean.PictureBean;
import com.ldlywt.base.view.BaseFragment;
import com.ldlywt.easyhttp.Call;
import com.ldlywt.easyhttp.Callback;
import com.ldlywt.easyhttp.HttpClient;
import com.ldlywt.easyhttp.Request;
import com.ldlywt.easyhttp.Response;
import com.ldlywt.simpleimageloader.org.simple.imageloader.cache.DoubleCache;
import com.ldlywt.simpleimageloader.org.simple.imageloader.config.ImageLoaderConfig;
import com.ldlywt.simpleimageloader.org.simple.imageloader.core.SimpleImageLoader;
import com.ldlywt.simpleimageloader.org.simple.imageloader.policy.ReversePolicy;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/24
 *     desc   :  * 图片加载Fragment，使用手写的http和手写的Imageload
 *     version: 1.0
 * </pre>
 */
public class ImagesFragment extends BaseFragment {

    private static final String URL = "http://image.baidu.com/channel/listjson?pn=0&rn=30&tag1=美女&tag2=全部&ie=utf8";
    private ImageItemAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.recycleview;
    }

    @Override
    public void initView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mAdapter = new ImageItemAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initImageLoader();
        HttpClient httpClient = new HttpClient();
        Request request = new Request
                .Builder()
                .url(URL)
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.i(response.getBody());
                Gson gson = new Gson();
                PictureBean pictureBean = gson.fromJson(response.getBody(), PictureBean.class);
                mActivity.runOnUiThread(() -> {
                    mAdapter.setList(pictureBean.data);
                    mAdapter.getRandomHeight(pictureBean.data);
                    mAdapter.notifyDataSetChanged();
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SimpleImageLoader.getInstance().stop();
    }

    private void initImageLoader() {
        ImageLoaderConfig config = new ImageLoaderConfig()
                .setLoadingPlaceholder(R.mipmap.loading)
                .setNotFoundPlaceholder(R.mipmap.not_found)
                .setCache(new DoubleCache(mActivity))
                .setThreadCount(4)
                .setLoadPolicy(new ReversePolicy());
        // 初始化
        SimpleImageLoader.getInstance().init(config);
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img);
        }
    }

    private class ImageItemAdapter extends RecyclerView.Adapter<ImageViewHolder> {

        private List<PictureBean.DataBean> list = new ArrayList<>();
        private List<Integer> mHeights;

        public ImageItemAdapter() {
        }

        public void setList(List<PictureBean.DataBean> list) {
            this.list = list;
        }

        public void getRandomHeight(List<PictureBean.DataBean> mList) {
            mHeights = new ArrayList<>();
            for (int i = 0; i < mList.size(); i++) {
                //随机的获取一个范围为200-600直接的高度
                mHeights.add((int) (300 + Math.random() * 400));
            }
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.image_item_layout, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            // 加载图片
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.height = mHeights.get(position);
            holder.itemView.setLayoutParams(layoutParams);
            SimpleImageLoader.getInstance().displayImage(holder.mImageView,
                    list.get(position).image_url);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}
