package com.yunge.myretrofitmvvm.java.manager

import android.util.Log
import com.yunge.myretrofitmvvm.java.RetrofitRxChromeManeger
import com.yunge.myretrofitmvvm.java.ServiceCreator
import com.yunge.myretrofitmvvm.java.config.HttpConfig
import com.yunge.myretrofitmvvm.java.interceptor.FilterInterceptor
import com.yunge.myretrofitmvvm.java.model.ResponseChromeName.DataChromeName
import com.yunge.myretrofitmvvm.java.model.ResponseChromeName
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

class RetrofitChromeName(val finish: OnFinish) {


    var imageNameLists = ArrayList<DataChromeName>()

    interface OnFinish{

        fun setImageName(dataList: List<DataChromeName>)
    }


    fun getObserver() {

        val map = HashMap<String, String>()
        map["c"] = "WallPaper"
        map["a"] = "getAllCategoriesV2"
//        map["order"] = "create_time"
//        map["start"] = "0"
//        map["count"] = "30"
        map["from"] = "360chrome"

        ServiceCreator.getService(DemoSevice::class.java).queryChromeName(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(createObserver())
    }

    private fun createObserver(): Observer<ResponseChromeName> {
        return object : Observer<ResponseChromeName> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onComplete() {
                Log.d(RetrofitRxChromeManeger.TAG, "onComplete")
            }

            override fun onNext(t: ResponseChromeName) {
                imageNameLists = t.data as ArrayList<DataChromeName>
                finish.setImageName(imageNameLists)
            }

            override fun onError(e: Throwable) {
                Log.d(RetrofitRxChromeManeger.TAG, "onError")
            }

        }
    }
}