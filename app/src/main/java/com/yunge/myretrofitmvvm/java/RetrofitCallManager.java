package com.yunge.myretrofitmvvm.java;

import android.util.Log;

import com.yunge.myretrofitmvvm.java.model.ResponseInfo;
import com.yunge.myretrofitmvvm.java.service.DemoSevice;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCallManager {

    public static final String TAG = "RetrofitCallManager";

    public static final String BASE_URL = "https://wanandroid.com/";

    public void createRet(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        DemoSevice service = retrofit.create(DemoSevice.class);
        Call<ResponseInfo> call = service.qureyUser();

        call.enqueue(new Callback<ResponseInfo>() {
            @Override
            public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                for (ResponseInfo.DataName name : response.body().getData()){
                    Log.d(TAG, name.getName() +"---"+ name.getOrder());
                }

            }

            @Override
            public void onFailure(Call<ResponseInfo> call, Throwable t) {
                Log.d(TAG,"请求失败了！");
            }
        });

    }
}
