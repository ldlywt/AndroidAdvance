package com.ldlywt.androidadvance.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ldlywt.androidadvance.Constant;
import com.ldlywt.androidadvance.R;
import com.ldlywt.androidadvance.bean.Weather;
import com.ldlywt.base.frame.http.HttpCallBack;
import com.ldlywt.base.frame.http.XHttp;
import com.ldlywt.base.view.BaseFragment;
import com.ldlywt.easyhttp.Call;
import com.ldlywt.easyhttp.Callback;
import com.ldlywt.easyhttp.HttpClient;
import com.ldlywt.easyhttp.Request;
import com.ldlywt.easyhttp.RequestBody;
import com.ldlywt.easyhttp.Response;
import com.ldlywt.ioc.annomation.event.OnClick;
import com.ldlywt.ioc.annomation.resouces.ViewById;
import com.ldlywt.ioc.manager.InjectManager;
import com.orhanobut.logger.Logger;

import java.io.IOException;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HttpFragment extends BaseFragment {

    private HttpClient mHttpClient;

    @ViewById(R.id.tv_show)
    private TextView mTvShow;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_http;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        InjectManager.inject(this);
        mHttpClient = new HttpClient();
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.bt_get, R.id.bt_post, R.id.bt_get2, R.id.bt_post2})
    public void open(View v) {
        switch (v.getId()) {
            case R.id.bt_get:
                get();
//                getWeather();
                break;
            case R.id.bt_post:
                post();
                break;
            case R.id.bt_get2:
//                get();
                getWeather();
                break;
            case R.id.bt_post2:

                break;
        }
    }

    private void get() {
        new Thread(() -> {
            Request request = new Request
                    .Builder()
                    .url("http://www.wanandroid.com/banner/json")
                    .build();
            Call call = mHttpClient.newCall(request);
            try {
                final Response response = call.execute();
//                Log.i(TAG, "get onResponse: " + response.getBody());
                Logger.json(response.getBody());
                getActivity().runOnUiThread(() -> mTvShow.setText(response.getBody()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void post() {
        RequestBody body = new RequestBody()
                .add("key", "064a7778b8389441e30f91b8a60c9b23")
                .add("city", "深圳");
        final Request request = new Request
                .Builder()
                .url("http://restapi.amap.com/v3/weather/weatherInfo")
                .post(body)
                .build();
        mHttpClient
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Logger.json(e.toString());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        Logger.json(response.getBody());
                        getActivity().runOnUiThread(() -> mTvShow.setText(response.getBody()));

                    }
                });
    }


    public void getWeather() {
        XHttp.getDefault().get(Constant.URL_WEATHER_GET, null, new HttpCallBack<Weather>() {
            //成功返回你传入的返回类型
            @Override
            public void onSuccess(Weather weather) {
                getActivity().runOnUiThread(() -> mTvShow.setText(weather.getData().getCity() + " \n温度：" +
                        weather.getData().getWendu() + "度 \n 提示：" +
                        weather.getData().getGanmao()));
            }

            @Override
            public void onFailed(String str) {
                Logger.e(str);
            }
        });
    }
}
