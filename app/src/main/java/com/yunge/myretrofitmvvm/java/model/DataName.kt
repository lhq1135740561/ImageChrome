package com.yunge.myretrofitmvvm.java.model

import com.google.gson.annotations.SerializedName
import org.litepal.LitePal
import org.litepal.crud.LitePalSupport

import com.yunge.myretrofitmvvm.java.model.ResponseChromeName.DataChromeName

class DataName(var name: String,var page: Int): LitePalSupport() {

    @Transient var id: Long = 0

}

class RepoDataList{

    fun getDataLists(): MutableList<DataName>{

        return LitePal.findAll(DataName::class.java)
    }


    //找到分类壁纸数据库
    fun getDataChromeLists(chromeId: String): MutableList<DataChrome>{

        return LitePal.where("class_id = ?",chromeId).find(DataChrome::class.java)
    }
}