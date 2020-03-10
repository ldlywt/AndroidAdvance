package com.ldlywt.androidadvance.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AidlActivity extends AppCompatActivity {

//    private IServiceManager mIServiceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindService();
        ServiceManager.getService("testService");
        ServiceManager.checkService("testService");
        String[] services = ServiceManager.listServices();


//        try {
//            mIServiceManager.getService("testService");
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }


    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.ldlywt.androidadvance", "com.ldlywt.androidadvance.TestService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            mIServiceManager = IServiceManager.Stub.asInterface(service);
            ServiceManager.addService(name.getClassName(), service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            mIServiceManager = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
