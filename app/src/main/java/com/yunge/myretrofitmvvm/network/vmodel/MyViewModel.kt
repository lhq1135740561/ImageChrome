package com.yunge.myretrofitmvvm.network.vmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yunge.myretrofitmvvm.java.model.ResponseInfo
import com.yunge.myretrofitmvvm.network.model.DataBean
import com.yunge.myretrofitmvvm.network.model.base.BaseResponseBody
import com.yunge.myretrofitmvvm.network.model.base.Weather
import com.yunge.myretrofitmvvm.network.repo.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel : ViewModel() {
//    private val TAG = MyViewModel::class.simpleName


    private val datas: MutableLiveData<ResponseInfo> by lazy {
        MutableLiveData<ResponseInfo>().also {
            loadDatas()
        }
    }

    private val repository = ArticleRepository()


    //返回数据持有类的对象
    fun getMutableLiveData(): LiveData<ResponseInfo> {

        return datas
    }

    private fun loadDatas() {
        GlobalScope.launch { Dispatchers.Main }
        suspend { getData() }
    }

    suspend fun getData() {
        val result = withContext(Dispatchers.IO) {
            repository.getData()
        }

        datas.value = result
    }


}