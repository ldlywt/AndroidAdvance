package com.ldlywt.androidadvancedemo.ui.fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.base.view.BaseFragment;
import com.ldlywt.views.RecyclerViewBanner;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
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
public class RvBannerFragment extends BaseFragment {

    public static Fragment newInstance(String from) {
        RvBannerFragment fragment = new RvBannerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_banner;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        RecyclerViewBanner recyclerViewBanner1 = (RecyclerViewBanner) getView().findViewById(R.id.rv_banner_1);
        RecyclerViewBanner recyclerViewBanner2 = (RecyclerViewBanner) getView().findViewById(R.id.rv_banner_2);
        RecyclerViewBanner recyclerViewBanner3 = (RecyclerViewBanner) getView().findViewById(R.id.rv_banner_3);
        RecyclerViewBanner recyclerViewBanner4 = (RecyclerViewBanner) getView().findViewById(R.id.rv_banner_4);
        RecyclerViewBanner recyclerViewBanner5 = (RecyclerViewBanner) getView().findViewById(R.id.rv_banner_5);

        final List<String> banners = new ArrayList<>();
        banners.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547793817916&di=e6233f17092b74e36878fe2f2b5e7edc&imgtype=0&src=http%3A%2F%2Fimg.pptjia.com%2Fimage%2F20180117%2Ff4b76385a3ccdbac48893cc6418806d5.jpg");
        banners.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547793817915&di=dce5240ab486a36055b39a71749db103&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F503d269759ee3d6d2a5b193849166d224e4adea0.jpg");
        banners.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547793817914&di=1c1fb3923df2b096c6eb3a620358e2d5&imgtype=0&src=http%3A%2F%2Fwww.jituwang.com%2Fuploads%2Fallimg%2F101028%2F290-10102PG5170.jpg");
        banners.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547793817904&di=59c76fb1c64c0fdb9d7a1d2b977bee1c&imgtype=0&src=http%3A%2F%2Fpic32.photophoto.cn%2F20140707%2F0034034894201836_b.jpg");
        banners.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547793817901&di=c606ba06edb0af56b3e8ca3205ae3425&imgtype=0&src=http%3A%2F%2Fa4.topitme.com%2Fo%2F201101%2F03%2F12939857045446.jpg");
        //recyclerViewBanner1.setRvAutoPlaying(false);
        recyclerViewBanner1.setRvBannerData(banners);
        recyclerViewBanner1.setOnSwitchRvBannerListener(new RecyclerViewBanner.OnSwitchRvBannerListener() {
            @Override
            public void switchBanner(int position, AppCompatImageView bannerView) {
                loadImage(position, bannerView, banners);
            }
        });
        recyclerViewBanner1.setOnRvBannerClickListener(new RecyclerViewBanner.OnRvBannerClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(), "position: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewBanner2.setRvBannerData(banners);
        recyclerViewBanner2.setOnSwitchRvBannerListener(new RecyclerViewBanner.OnSwitchRvBannerListener() {
            @Override
            public void switchBanner(int position, AppCompatImageView bannerView) {
                loadImage(position, bannerView, banners);
            }
        });
        recyclerViewBanner3.setRvBannerData(banners);
        recyclerViewBanner3.setOnSwitchRvBannerListener(new RecyclerViewBanner.OnSwitchRvBannerListener() {
            @Override
            public void switchBanner(int position, AppCompatImageView bannerView) {
                loadImage(position, bannerView, banners);
            }
        });
        recyclerViewBanner4.setIndicatorInterval(2000);
        recyclerViewBanner4.setRvBannerData(banners);
        recyclerViewBanner4.setOnSwitchRvBannerListener(new RecyclerViewBanner.OnSwitchRvBannerListener() {
            @Override
            public void switchBanner(int position, AppCompatImageView bannerView) {
                loadImage(position, bannerView, banners);
            }
        });
        recyclerViewBanner5.setRvBannerData(banners);
        recyclerViewBanner5.setOnSwitchRvBannerListener(new RecyclerViewBanner.OnSwitchRvBannerListener() {
            @Override
            public void switchBanner(int position, AppCompatImageView bannerView) {
                loadImage(position, bannerView, banners);
            }
        });
    }

    private void loadImage(int position, AppCompatImageView bannerView, List<String> banners) {
        Glide.with(bannerView.getContext())
                .load(banners.get(position))
                .placeholder(R.mipmap.ic_launcher)
                .into(bannerView);
    }


    @Override
    public void initView() {
    }


}
