package com.yunge.myretrofitmvvm.java.repo

import com.yunge.myretrofitmvvm.java.db.ChromeDao
import com.yunge.myretrofitmvvm.java.model.ResponseChromeName.DataChromeName

class ChromeRepository constructor(private val chromeDao: ChromeDao) {



    fun getChromNameList(): List<DataChromeName>? {

        return chromeDao.getChromeNameList()

    }


}