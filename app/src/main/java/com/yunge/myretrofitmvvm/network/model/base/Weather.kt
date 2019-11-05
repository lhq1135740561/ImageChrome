package com.yunge.myretrofitmvvm.network.model.base

import com.google.gson.annotations.SerializedName

class Weather(var data: InnerWeather) {

    class InnerWeather(
        @SerializedName("weather") var weatherList: NearestWeather
    ) {

        class NearestWeather(
            var date: String,
            var week: String,
            var nongli: String,
            var info: InfoBean
        ) {

            class InfoBean(
                var dawn: List<String>,
                var day: List<String>,
                var night: List<String>
            )
        }
    }
}