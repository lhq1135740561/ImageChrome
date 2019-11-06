package com.yunge.myretrofitmvvm.java.repo

import android.util.Log
import android.widget.Toast
import com.yunge.myretrofitmvvm.MainApplication
import com.yunge.myretrofitmvvm.java.ServiceCreator
import com.yunge.myretrofitmvvm.java.db.ChromeDao
import com.yunge.myretrofitmvvm.java.model.DataName
import com.yunge.myretrofitmvvm.java.model.RepoDataList
import com.yunge.myretrofitmvvm.java.model.ResponseChromeName
import com.yunge.myretrofitmvvm.java.model.ResponseChromeName.DataChromeName
import com.yunge.myretrofitmvvm.java.service.DemoSevice
import com.yunge.myretrofitmvvm.ui.ImageNameActivity
import com.yunge.myretrofitmvvm.ui.adapter.ImageNameAdapter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.litepal.LitePal

class ChromeNetwork(val finish: OnFinish,val chromeDao: ChromeDao) {

    interface OnFinish {

        fun setImageName(dataList: List<DataChromeName>)
    }

    private var imageNameList = ArrayList<DataChromeName>()


    private val chromeNameService = ServiceCreator.getService(DemoSevice::class.java)



    fun fetchChromNameList() =
        chromeNameService.queryChromeName(chromeNameMap())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseChromeName>{
                override fun onComplete() {
                    Log.d(TAG,"onComplete ---ChromeNetwork")
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: ResponseChromeName) {

                    imageNameList = t.data as ArrayList<DataChromeName>

                    //获取数据库对象
                    var dataLists = RepoDataList().getDataLists()
                    //当数据库为空时，添加数据
                    if(dataLists.isNullOrEmpty() && dataLists.size == 0 && imageNameList.isNotEmpty()) {
                        Log.d(ImageNameActivity.TAG,"数据库添加数据")
                        for (data: DataChromeName in imageNameList) {
                            val dataName = DataName(data.name, data.page_id)
                            dataName.save()
                            dataLists.add(dataName)
                        }
                    }

                    finish.setImageName(imageNameList)
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG,"onError ---ChromeNetwork")
                    Toast.makeText(MainApplication.context,"网络请求失败",Toast.LENGTH_SHORT).show()
                }
            })


    //返回接口参数列表
    private fun chromeNameMap(): Map<String, String> {

        val map = HashMap<String, String>()
        map["c"] = "WallPaper"
        map["a"] = "getAllCategoriesV2"
//        map["order"] = "create_time"
//        map["start"] = "0"
//        map["count"] = "30"
        map["from"] = "360chrome"

        return map
    }


    companion object {

        const val TAG = "ChromeNetwork"
    }

}