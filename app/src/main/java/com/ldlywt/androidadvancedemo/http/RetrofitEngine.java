package com.ldlywt.androidadvancedemo.http;

import com.ldlywt.androidadvancedemo.bean.Weather;
import com.ldlywt.base.frame.http.HttpCallBack;
import com.ldlywt.base.frame.http.IHttpEngine;
import com.orhanobut.logger.Logger;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/31
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class RetrofitEngine implements IHttpEngine {

    private Retrofit mRetrofit;
    //    String url = "http://wthrcdn.etouch.cn/weather_mini?citykey=101010100";
    String url = "http://wthrcdn.etouch.cn/";

    public RetrofitEngine() {
        mRetrofit = new Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void get(String url, Map<String, Object> params, final HttpCallBack callBack) {
        mRetrofit
                .create(ApiService.class)
                .getWeather(101010100)
                .enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Logger.i(response.body().toString());
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }

    @Override
    public void post(String url, Map<String, Object> params, HttpCallBack callBack) {

    }

    public interface ApiService {
        @GET("weather_mini")
        Call<Weather> getWeather(@Query("citykey") int citykey);
    }
}
