package com.yunge.myretrofitmvvm.java.service;

import com.yunge.myretrofitmvvm.java.config.HttpConfig;
import com.yunge.myretrofitmvvm.java.model.ResponseChrome;
import com.yunge.myretrofitmvvm.java.model.ResponseChromeName;
import com.yunge.myretrofitmvvm.java.model.ResponseInfo;
import com.yunge.myretrofitmvvm.java.model.ResponseWeather;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface DemoSevice {

    //Call定义User用户的接口
    @GET("wxarticle/chapters/json")
    Call<ResponseInfo> qureyUser();


    //Rxjava定义User用户的接口
    @GET("wxarticle/chapters/json")
    Observable<ResponseInfo> qureyUserRx();


    //Rxjava定义天气接口
    @Headers(HttpConfig.HTTP_REQUEST_TYPE_KEY + ":" + HttpConfig.HTTP_REQUEST_WEATHER)
    @GET("onebox/weather/query")
    Observable<ResponseWeather> queryWeather(@QueryMap Map<String,String> params);


    //Rxjava定义360图片接口
//    @Headers(HttpConfig.HTTP_REQUEST_TYPE_KEY + ":" + HttpConfig.HTTP_REQUEST_CHROME)
    @GET("index.php")
    Observable<ResponseChrome> queryChrome(@QueryMap Map<String,String> params);


    //360壁纸名称接口
    @GET("index.php")
    Observable<ResponseChromeName> queryChromeName(@QueryMap Map<String,String> params);
}
