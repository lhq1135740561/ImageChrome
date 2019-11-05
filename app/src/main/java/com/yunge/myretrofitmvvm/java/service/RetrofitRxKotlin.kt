package com.yunge.myretrofitmvvm.java.service

import android.util.Log
import android.widget.Toast
import com.yunge.myretrofitmvvm.MainApplication
import com.yunge.myretrofitmvvm.java.config.HttpConfig
import com.yunge.myretrofitmvvm.java.interceptor.FilterInterceptor
import com.yunge.myretrofitmvvm.java.model.ResponseWeather
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitRxKotlin {

    companion object {
        const val TAG = "RetrofitRxKotlin"
        const val READ_TIMEOUT = 10000L
        const val WRITE_TIMEOUT = 10000L
        const val CONNECT_TIMEOUT = 10000L

        fun getInstance(): RetrofitRxKotlin {
            return Holder.instance
        }
    }

    //私有
    private object Holder {
        val instance = RetrofitRxKotlin()
    }

    /**
     * Retrofit设置单例模式
     */
    private fun createRetrofit(): Retrofit {
        val builder = OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(FilterInterceptor())

        val client = builder.build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(HttpConfig.BASE_URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


    /**
     * 获取API接口和RxJava观察者模式
     */
    fun getService(city: String) {

        val service = createRetrofit().create(DemoSevice::class.java)
        val map = HashMap<String,String>()
        map["cityname"] = city

        service.queryWeather(map).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(createObserver())
    }


    //创建观察者
    private fun createObserver(): Observer<ResponseWeather> {

        return object : Observer<ResponseWeather> {
            override fun onComplete() {
//                Toast.makeText(MainApplication.context, "onComplete", Toast.LENGTH_SHORT).show()
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: ResponseWeather) {
                //获取realtime对象
                val realtime = t.result.data.realtime

                val cityName = realtime.city_name
                val time = realtime.time
                val tmp = realtime.weather.tmp
                val info = realtime.weather.info

                    Log.d(TAG, "$cityName,$info,时间：$time,当前温度：$tmp")

                for (weather: ResponseWeather.Result.Data.Weather in t.result.data.weather) {
                    Log.d(TAG, "${weather.date} ----星期${weather.week}---${weather.nongli}")
                }
            }

            override fun onError(e: Throwable) {
            }

        }
    }


}