package com.yunge.myretrofitmvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yunge.myretrofitmvvm.MainActivity
import com.yunge.myretrofitmvvm.R
import com.yunge.myretrofitmvvm.java.db.ChromeDao
import com.yunge.myretrofitmvvm.java.model.DataName
import com.yunge.myretrofitmvvm.java.model.RepoDataList
import com.yunge.myretrofitmvvm.java.model.ResponseChromeName.DataChromeName
import com.yunge.myretrofitmvvm.java.repo.ChromeNetwork
import com.yunge.myretrofitmvvm.ui.adapter.ImageLitepalNameAdapter
import com.yunge.myretrofitmvvm.ui.adapter.ImageNameAdapter
import kotlinx.android.synthetic.main.activity_image.*
import org.litepal.LitePal
import org.litepal.extension.delete
import org.litepal.tablemanager.callback.DatabaseListener


class ImageNameActivity : BaseActivity(), ChromeNetwork.OnFinish,
    ImageNameAdapter.OnImageNameClickLisenter {


    override fun OnItemClick(position: Int, id: Int, title: String) {
        //跳转传值id
//        MainActivity.actionStart(this,id.toString(),MainActivity::class.java)

        startActivity2(MainActivity::class.java, id.toString(), title)

        Toast.makeText(this, "$position---$id", Toast.LENGTH_SHORT).show()
    }


    override fun setImageName(dataList: List<DataChromeName>) {

        if(dataNameLists.isNullOrEmpty() && dataNameLists.size == 0) {
            manegerLayout = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            image_rv.layoutManager = manegerLayout
            imageNameAdapter = ImageNameAdapter(dataList, this, this)
            image_rv.adapter = imageNameAdapter
            Toast.makeText(this, "数据为空", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        const val TAG = "ImageNameActivity"
    }

    private val chromeDao = ChromeDao()

    private val chromeName = ChromeNetwork(this, chromeDao)

    private lateinit var imageNameAdapter: ImageNameAdapter

    private lateinit var litepalNameAdapter: ImageLitepalNameAdapter  //本地数据适配器

    private lateinit var manegerLayout: StaggeredGridLayoutManager

    //获取壁纸名称数据库
    var dataNameLists = RepoDataList().getDataLists()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        //litepal监听
        LitePal.registerDatabaseListener(object : DatabaseListener {
            override fun onCreate() {
                Log.d(TAG, "litepal -- onCreate")
            }

            override fun onUpgrade(oldVersion: Int, newVersion: Int) {
                Log.d(TAG, "litepal -- onUpgrade")
            }
        })

        chromeName.fetchChromNameList()

        initLitePalChromeName()


//        var dataLists = RepoDataList().getDataLists()
//        val dataName = DataName("刘备",234)
//        dataName.save()
//        dataLists.add(dataName)

//        LitePal.delete<DataName>(1)

        var dataLists = RepoDataList().getDataLists()
        if (dataLists.isNotEmpty() && dataLists.size > 0) {
            dataLists.forEach {
                Log.d(TAG, "${it.id}--作者：${it.name}---页数：${it.page}")
            }
        }
    }

    //初始化本地壁纸名称数据库
    private fun initLitePalChromeName() {

        if (dataNameLists.isNotEmpty() && dataNameLists.size > 0) {

            manegerLayout = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            image_rv.layoutManager = manegerLayout
            litepalNameAdapter = ImageLitepalNameAdapter(dataNameLists, this, this)
            image_rv.adapter = litepalNameAdapter
            Toast.makeText(this, "数据不为空", Toast.LENGTH_SHORT).show()
        }
    }

}
