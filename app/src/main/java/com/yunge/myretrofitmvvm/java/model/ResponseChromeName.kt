package com.yunge.myretrofitmvvm.java.model

import com.google.gson.annotations.SerializedName
import org.litepal.crud.LitePalSupport

class ResponseChromeName (var errmsg: String,var total: Int,var data: MutableList<DataChromeName>) {

    class DataChromeName(@SerializedName("id") var page_id: Int, var name: String) : LitePalSupport(){
        @Transient val id: Long = 0
    }
}