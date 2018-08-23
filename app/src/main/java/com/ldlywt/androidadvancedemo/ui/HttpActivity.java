package com.ldlywt.androidadvancedemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.easyhttp.Call;
import com.ldlywt.easyhttp.Callback;
import com.ldlywt.easyhttp.HttpClient;
import com.ldlywt.easyhttp.Request;
import com.ldlywt.easyhttp.RequestBody;
import com.ldlywt.easyhttp.Response;
import com.ldlywt.ioc.annomation.event.OnClick;
import com.ldlywt.ioc.annomation.resouces.ContentViewById;
import com.ldlywt.ioc.annomation.resouces.ViewById;
import com.ldlywt.ioc.manager.InjectManager;

import java.io.IOException;

@ContentViewById(R.layout.activity_http)
public class HttpActivity extends AppCompatActivity {
    public static final String TAG = "HttpActivity";

    private HttpClient mHttpClient;

    @ViewById(R.id.tv_show)
    private TextView mTvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.inject(this);
//        mHttpClient = new HttpClient
//                .Builder()
//                .setRetryTimes(3)
//                .build();
        mHttpClient = new HttpClient();
//        findViewById(R.id.bt_get).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                get();
//            }
//        });
//
//        findViewById(R.id.bt_post).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                post();
//            }
//        });
    }

    @OnClick({R.id.bt_get, R.id.bt_post})
    public void open(View v) {
        switch (v.getId()) {
            case R.id.bt_get:
//                Toast.makeText(this, "xxxx", Toast.LENGTH_SHORT).show();
                get();
                break;
            case R.id.bt_post:
                post();
                break;
        }
    }

    private void get() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request
                        .Builder()
                        .url("http://www.wanandroid.com/banner/json")
                        .build();
                Call call = mHttpClient.newCall(request);
                try {
                    final Response response = call.execute();
                    Log.i(TAG, "get onResponse: " + response.getBody());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTvShow.setText(response.getBody());
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                        Log.i(TAG, "onFailure: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        Log.i(TAG, "post onResponse: " + response.getBody());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTvShow.setText(response.getBody());
                            }
                        });

                    }
                });
    }
}
