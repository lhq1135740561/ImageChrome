package com.yunge.myretrofitmvvm.java.model

class ResponseChrome (var errno: String,var errmsg: String,var data: List<Data>){

    class Data(var class_id: String,var url: String,var utag: String,var img_1024_768: String)
}