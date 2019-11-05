package com.yunge.myretrofitmvvm.java.manager

import com.yunge.myretrofitmvvm.java.service.RetrofitRxKotlin
import java.io.Serializable

/**
 * 定义一个密封类，用于返回类的实例对象
 */
sealed class RetrofitManager {

//    object RetrofitRxKotlin : RetrofitManager()
    object A : RetrofitManager()
    object B : RetrofitManager()

}


fun execute(manager: RetrofitManager) = when(manager){

//    RetrofitManager.RetrofitRxKotlin -> RetrofitRxKotlin()
    RetrofitManager.A -> A()
    RetrofitManager.B -> B()



}

interface Singleton{

    fun work()

}

class A : Singleton{
    override fun work() {

    }

}

class B : Singleton{
    override fun work() {

    }

}