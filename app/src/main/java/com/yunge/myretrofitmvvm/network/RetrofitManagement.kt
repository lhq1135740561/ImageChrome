package com.yunge.myretrofitmvvm.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.yunge.myretrofitmvvm.network.service.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManagement {


    object RetrofitClient{
        val BASE_URL = "https://wanandroid.com/"
        //接口对象
        val apiService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())  //设置数据解析器
                .addCallAdapterFactory(CoroutineCallAdapterFactory()) //添加一个支持服务方法返回类型的调用适配器工厂
                .build()

            return@lazy retrofit.create(ApiService::class.java)
        }
    }

}