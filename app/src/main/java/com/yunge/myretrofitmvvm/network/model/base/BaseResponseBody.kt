package com.yunge.myretrofitmvvm.network.model.base

import com.google.gson.annotations.SerializedName

class BaseResponseBody<T>(@SerializedName("error_code") var code: Int,
                          @SerializedName("reason") var msg: String,
                          @SerializedName("result") var data: T){

}