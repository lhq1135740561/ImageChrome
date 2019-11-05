package com.yunge.myretrofitmvvm.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.yunge.myretrofitmvvm.R

abstract class BaseActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    private fun getLContext(): Context = this


    //跳转到另一个页面
    fun <T> startActivity2(clz: Class<T>,id: String,title: String){

        getLContext().startActivity(Intent(getLContext(),clz).putExtra("id",id).putExtra("title",title))
    }

    fun <T> startActivity(clz: Class<T>,title: String,url: String){

        getLContext().startActivity(Intent(getLContext(),clz).putExtra("title",title).putExtra("url",url))
    }

    fun <T> startActivity(clz: Class<T>){

        getLContext().startActivity(Intent(getLContext(),clz))
    }
}
