package com.yunge.myretrofitmvvm.java.config

class HttpConfig {

    companion object{

        const val HTTP_REQUEST_TYPE_KEY = "requstType"

        const val KEY = "key"

        //Weather
        //https://op.juhe.cn/onebox/weather/query?cityname=南昌&key=afc28ae28c6f1b520dab5d1ed537f6c0

        const val BASE_URL_WEATHER = "https://op.juhe.cn/"

        const val MY_KEY = "afc28ae28c6f1b520dab5d1ed537f6c0"

        const val HTTP_REQUEST_WEATHER = "weather"


       // http://wallpaper.apc.360.cn/index.php?c=WallPaper&a=getAppsByOrder&order=create_time&start=0&count=30&from=360chrome
        //最新360壁纸
        const val BASE_URL_CGROME = "http://wallpaper.apc.360.cn/"

        //参数 a 不同
        const val CGROME_CREATE_A = "getAppsByOrder"

        //360壁纸分类名称
        //  http://cdn.apc.360.cn/index.php?c=WallPaper&a=getAllCategoriesV2&from=360chrome
        const val BASE_URL_CGROME_NAME = "http://cdn.apc.360.cn/"

        const val CGROME_NAME_A = "getAllCategoriesV2"

        //360壁纸分类壁纸
        //http://wallpaper.apc.360.cn/index.php?c=WallPaper&a=getAppsByCategory&cid=16&start=0&count=30&from=360chrome

        const val CGROME_IMAGE_A = "getAppsByCategory"

    }

}