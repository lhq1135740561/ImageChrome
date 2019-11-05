package com.yunge.myretrofitmvvm.java;

import android.util.Log;

import com.yunge.myretrofitmvvm.BuildConfig;
import com.yunge.myretrofitmvvm.java.config.HttpConfig;
import com.yunge.myretrofitmvvm.java.interceptor.FilterInterceptor;
import com.yunge.myretrofitmvvm.java.model.ResponseWeather;
import com.yunge.myretrofitmvvm.java.service.DemoSevice;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRxWeaManager {

    public static final String TAG = "RetrofitRxWeaManager";

    public static final int READ_TIMEOUT = 10000;

    public static final int WRITE_TIMEOUT = 10000;

    public static final int CONNECT_TIMEOUT = 10000;

    public void createRetrofit(String city){

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new FilterInterceptor());



//        if (BuildConfig.DEBUG) {
//            Log.d("FilterInterceptor", "----DEBUG");
//            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            builder.addInterceptor(httpLoggingInterceptor);
////            builder.addInterceptor(new MonitorInterceptor(ContextHolder.getContext()));
//        }

        OkHttpClient client = builder.build();


        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(HttpConfig.BASE_URL_WEATHER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        DemoSevice sevice = retrofit.create(DemoSevice.class);

        Map<String,String> map = new HashMap<>();
        map.put("cityname",city);

        sevice.queryWeather(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseWeather>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseWeather responseWeather) {
                        for (ResponseWeather.Result.Data.Weather weather: responseWeather.getResult().getData().getWeather()){
                            Log.d(TAG, weather.getDate() + "---星期" + weather.getWeek());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"结束");
                    }
                });


    }
}
