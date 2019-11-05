package com.yunge.myretrofitmvvm.java

import android.util.Log
import com.yunge.myretrofitmvvm.java.config.HttpConfig
import com.yunge.myretrofitmvvm.java.interceptor.FilterInterceptor
import com.yunge.myretrofitmvvm.java.model.ResponseChromeName
import com.yunge.myretrofitmvvm.java.model.ResponseChromeName.DataChromeName
import com.yunge.myretrofitmvvm.java.service.DemoSevice
import com.yunge.myretrofitmvvm.java.service.RetrofitRxKotlin
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitRxChromeManeger(){



    companion object {

        const val TAG = "RetrofitRxChromeManeger"

        //单例模式
//        fun getInstance(): RetrofitRxChromeManeger {
//            return
//        }
    }

    private object Holder {
//        val instance = RetrofitRxChromeManeger()
    }



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
            .baseUrl(HttpConfig.BASE_URL_CGROME)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


    /**
     * 获取service接口
     */
    fun <T> getService(clz: Class<T>): T {


        return createRetrofit().create(clz)

    }


}