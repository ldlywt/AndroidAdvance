package com.ldlywt.androidadvancedemo.ui.fragment.maintab;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.base.view.BaseFragment;

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

//    @Inject
//    OkHttpClient mOkHttpClient;


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
//        DaggerDiscoverFragmentComponent.create().inject(this);
//        Request request = new Request.Builder().url(Constant.URL_WEATHER_GET).build();
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Logger.i(response.message());
//            }
//        });
    }

    @Override
    public void initView() {
        getView().setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
    }
}
