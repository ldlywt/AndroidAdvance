//package com.ldlywt.androidadvancedemo.dagger;
//
//import dagger.Module;
//import dagger.Provides;
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//
///**
// * <pre>
// *     author : wutao
// *     e-mail : ldlywt@163.com
// *     time   : 2018/09/02
// *     desc   :
// *     version: 1.0
// * </pre>
// */
//@Module
//public class DiscoveryFragmentModule {
//    private int cacheSize;
//
//    public DiscoveryFragmentModule(int cacheSize) {
//        this.cacheSize = cacheSize;
//    }
//
//    @Provides
//    OkHttpClient provideOkHttpClient(){
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        return okHttpClient;
//    }
//
//    @Provides
//    Retrofit provideRetrofit(OkHttpClient client){
//        return new Retrofit.Builder().client(client).build();
//    }
//
//}
