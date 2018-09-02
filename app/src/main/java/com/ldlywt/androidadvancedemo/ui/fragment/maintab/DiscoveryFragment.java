package com.ldlywt.androidadvancedemo.ui.fragment.maintab;

import android.os.Bundle;

import com.ldlywt.androidadvancedemo.Constant;
import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.androidadvancedemo.dagger.DaggerDiscoverFragmentComponent;
import com.ldlywt.base.view.BaseFragment;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class DiscoveryFragment extends BaseFragment{

    @Inject
    OkHttpClient mOkHttpClient;


    public static DiscoveryFragment newInstance(String from) {
        DiscoveryFragment fragment = new DiscoveryFragment();
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
        DaggerDiscoverFragmentComponent.create().inject(this);
        Request request = new Request.Builder().url(Constant.URL_WEATHER_GET).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.i(response.message());
            }
        });
    }

    @Override
    public void initView() {

    }
}
