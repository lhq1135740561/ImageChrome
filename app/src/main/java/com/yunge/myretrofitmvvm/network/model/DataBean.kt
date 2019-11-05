package com.yunge.myretrofitmvvm.network.model

class DataBean(var errorMsg: String,var errorCode: Int,var data: List<Data>){


    class Data(var id: Int,var name: String,var order: Int)


}