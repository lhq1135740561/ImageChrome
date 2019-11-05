package com.yunge.myretrofitmvvm.java;

import android.util.Log;

import com.yunge.myretrofitmvvm.java.model.ResponseInfo;
import com.yunge.myretrofitmvvm.java.service.DemoSevice;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRxManager {

    public static final String TAG = "RetrofitRxManager";

    public static final String BASE_URL = "https://wanandroid.com/";

    public void createRetrofitRx() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        DemoSevice sevice = retrofit.create(DemoSevice.class);

        sevice.qureyUserRx()
                .doOnNext(new Consumer<ResponseInfo>() {
            @Override
            public void accept(ResponseInfo responseInfo) throws Exception {
                // 尝试修正 User 数据
                responseInfo.getData().add(new ResponseInfo.DataName("卢海强",190014));
            }
        })
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseInfo responseInfo) {
                        for (ResponseInfo.DataName name : responseInfo.getData()) {
                            Log.d(TAG, name.getName() + "---" + name.getOrder());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

    }
}
