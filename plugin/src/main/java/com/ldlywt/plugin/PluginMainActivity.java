package com.ldlywt.plugin;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

public class PluginMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("wutao", "onCreate: 我是插件的Activity");
    }

    @Override
    public Resources getResources() {
        if (getApplication() != null && getApplication().getResources() != null) {
            //这个实际返回的是我们自己创建的 resources
            return getApplication().getResources();
        }
        return super.getResources();
    }
}
