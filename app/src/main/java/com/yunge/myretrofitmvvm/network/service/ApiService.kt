package com.yunge.myretrofitmvvm.network.service

import com.yunge.myretrofitmvvm.java.model.ResponseInfo
import com.yunge.myretrofitmvvm.network.model.DataBean
import com.yunge.myretrofitmvvm.network.model.base.BaseResponseBody
import com.yunge.myretrofitmvvm.network.model.base.Weather
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiService {

    @GET("wxarticle/chapters/json")
    fun queryWeather() : Deferred<ResponseInfo>

//    https://wanandroid.com/wxarticle/chapters/json

}