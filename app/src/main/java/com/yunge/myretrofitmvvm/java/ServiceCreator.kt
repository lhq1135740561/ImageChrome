package com.yunge.myretrofitmvvm.java

import com.yunge.myretrofitmvvm.java.config.HttpConfig
import com.yunge.myretrofitmvvm.java.interceptor.FilterInterceptor
import com.yunge.myretrofitmvvm.java.service.RetrofitRxKotlin
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceCreator {

    //返回Retrofit实例对象
    private fun createRetrofit(): Retrofit {
        val builder = OkHttpClient.Builder()
            .readTimeout(RetrofitRxKotlin.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(RetrofitRxKotlin.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(RetrofitRxKotlin.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(FilterInterceptor())
        val client = builder.build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(HttpConfig.BASE_URL_CGROME_NAME)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    /**
     * 获取service接口
     */
    fun <T : Any> getService(clz: Class<T>): T {


        return createRetrofit().create(clz)

    }
}