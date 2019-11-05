package com.yunge.myretrofitmvvm

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import org.litepal.LitePal


class MainApplication : Application() {

    //添加MainApplication到Manifest.xml中

    override fun onCreate() {
        super.onCreate()
        context = this
        LitePal.initialize(this)

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

    }
}