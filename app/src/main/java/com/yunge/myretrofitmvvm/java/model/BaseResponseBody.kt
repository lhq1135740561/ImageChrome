package com.yunge.myretrofitmvvm.java.model

import com.google.gson.annotations.SerializedName

class BaseResponseBody<T> (var errmsg: String,@SerializedName("data") var t: T)