package com.yunge.myretrofitmvvm.java.model

import com.google.gson.annotations.SerializedName
import com.yunge.myretrofitmvvm.network.model.base.Weather

class ResponseWeather(var error_code: Int, var reason: String, var result: Result) {

    class Result(var data: Data) {

        class Data(var weather: List<Weather>,var realtime: Realtime) {

            //实时的天气
            class Realtime(var city_name: String,var time: String,var weather: Weather){
                class Weather(
                    @SerializedName("temperature") var tmp:String,
                    var info: String)
            }

            class Weather(
                var date: String,
                var week: String,
                var nongli: String
            )
        }



    }
}