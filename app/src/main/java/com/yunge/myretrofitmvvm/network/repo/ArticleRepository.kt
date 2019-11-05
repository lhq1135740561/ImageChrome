package com.yunge.myretrofitmvvm.network.repo

import com.yunge.myretrofitmvvm.java.model.ResponseInfo
import com.yunge.myretrofitmvvm.network.RetrofitManagement.RetrofitClient
import com.yunge.myretrofitmvvm.network.model.DataBean
import com.yunge.myretrofitmvvm.network.model.base.BaseResponseBody
import com.yunge.myretrofitmvvm.network.model.base.Weather

class ArticleRepository {
    //suspend 将一个函数或 lambda 表达式标记为挂起式（可用做协程）
    suspend fun getData(): ResponseInfo {
        return RetrofitClient.apiService.queryWeather().await()
    }

}