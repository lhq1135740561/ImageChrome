package com.yunge.myretrofitmvvm.java.interceptor

import android.text.TextUtils
import com.yunge.myretrofitmvvm.java.config.HttpConfig
import okhttp3.Interceptor
import okhttp3.Response

//拦截器
class FilterInterceptor : Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        //获取request
        val request = chain.request()
        //获取HttpBuilder
        val httpBuilder = request.url().newBuilder()

        //获取Headers对象
        val headers = request.headers()
        if (headers.size() > 0){
            val requestType = headers.get(HttpConfig.HTTP_REQUEST_TYPE_KEY)
            if (!TextUtils.isEmpty(requestType)){
                when(requestType){
                    HttpConfig.HTTP_REQUEST_WEATHER ->
                        //添加参数 key值
                        httpBuilder.addEncodedQueryParameter(HttpConfig.KEY,HttpConfig.MY_KEY)
                }
            }
        }

        //获取requestBuilder新地址
        val requestBuilder = request.newBuilder().url(httpBuilder.build())

        //捕获新的请求
        return chain.proceed(requestBuilder.build())  //返回Response对象

    }

}