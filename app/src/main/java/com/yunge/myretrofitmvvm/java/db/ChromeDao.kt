package com.yunge.myretrofitmvvm.java.db

import com.yunge.myretrofitmvvm.java.model.ResponseChromeName.DataChromeName
import org.litepal.LitePal


/**
 * 360壁纸本地数据库
 */
class ChromeDao {

    fun getChromeNameList(): List<DataChromeName>? = LitePal.findAll(DataChromeName::class.java)

    fun savaChromeNameList(chromeNameList: List<DataChromeName>?){
        if (chromeNameList != null && chromeNameList.isNullOrEmpty()){
            LitePal.saveAll(chromeNameList)
        }
    }
}