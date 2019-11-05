package com.yunge.myretrofitmvvm

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.nostra13.universalimageloader.core.assist.QueueProcessingType
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer
import com.yunge.myretrofitmvvm.java.RetrofitCallManager
import com.yunge.myretrofitmvvm.java.RetrofitRxChromeManeger
import com.yunge.myretrofitmvvm.java.RetrofitRxManager
import com.yunge.myretrofitmvvm.java.RetrofitRxWeaManager
import com.yunge.myretrofitmvvm.java.config.HttpConfig
import com.yunge.myretrofitmvvm.java.model.DataChrome
import com.yunge.myretrofitmvvm.java.model.RepoDataList
import com.yunge.myretrofitmvvm.java.model.ResponseChrome
import com.yunge.myretrofitmvvm.java.model.ResponseChromeName.DataChromeName
import com.yunge.myretrofitmvvm.java.service.DemoSevice
import com.yunge.myretrofitmvvm.java.service.RetrofitRxKotlin
import com.yunge.myretrofitmvvm.network.vmodel.MyViewModel
import com.yunge.myretrofitmvvm.ui.BaseActivity
import com.yunge.myretrofitmvvm.ui.ImagePreviewActivity
import com.yunge.myretrofitmvvm.ui.adapter.ImageAdapter
import com.yunge.myretrofitmvvm.ui.adapter.litepal.ImageLitepalAdapter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.image_item.*
import org.litepal.LitePal

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : BaseActivity(), ImageAdapter.OnClickLister {


    override fun onClick(position: Int, title: String, url: String) {

        startActivity(ImagePreviewActivity::class.java, title, url)

//        startActivity(ImagePreviewActivity::class.java)
        Log.d(TAG, title)
    }


    companion object {
        const val TAG = "MainActivity"

        fun <T> actionStart(context: Context, id: String, clazz: Class<T>) {
            val intent = Intent(context, clazz)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }

    private val retrofitManager = RetrofitCallManager()

    private val retrofitRxManager = RetrofitRxManager()

    private val retrofitWeaManager = RetrofitRxWeaManager()

    private val retrofitRxKotlin = RetrofitRxKotlin()

    private val retrofitLists = ArrayList<RetrofitRxKotlin>()

    private val imageLoader: ImageLoader = ImageLoader.getInstance()

    private val url = "https://cn.bing.com/th?id=OIP.UmiwejL0PKV8HVbrmGxLzwHaNK&pid=Api&rs=1"

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MyViewModel::class.java)
    }

    /**
     * 图片适配器
     */
    private val chromeManeger = RetrofitRxChromeManeger()

    private lateinit var imageAdapter: ImageAdapter

    private lateinit var manager: LinearLayoutManager

    private var imageLists = ArrayList<ResponseChrome.Data>()

    private var imageListNames = ArrayList<DataChromeName>()

    private lateinit var recyclerView: RecyclerView

    //懒加载方式创建数据库对象
    private lateinit var dataChromeLists: MutableList<DataChrome>
    private lateinit var imageLitepalAdapter: ImageLitepalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.main_rv)


        //获取上一个活动传过来的ID
        val imageNameId = intent.getStringExtra("id")
        val title = intent.getStringExtra("title")




        if (imageNameId == null && title == null) {
            dataChromeLists = RepoDataList().getDataChromeLists("6")

            if (dataChromeLists.isNullOrEmpty()) {
                main_tb.title = "美女模特"
                initAdapter("6", HttpConfig.CGROME_IMAGE_A, 100) //初始化适配器
                Toast.makeText(this, "数据为空，请求数据", Toast.LENGTH_SHORT).show()
            }else{
                initLitepalChromeAdapter()
            }
        } else {
            //实例化数据库
            dataChromeLists = RepoDataList().getDataChromeLists(imageNameId)

            if (dataChromeLists.isNullOrEmpty()) {
                main_tb.title = title
                initAdapter(imageNameId, HttpConfig.CGROME_IMAGE_A, 100) //初始化适配器
                Toast.makeText(this, "数据为空，请求数据", Toast.LENGTH_SHORT).show()
            }else{
                initLitepalChromeAdapter()
            }
        }


        dataChromeLists.forEach {
            Log.d(TAG, "${it.id}--作者：${it.utag}")
        }


    }

    private fun initLitepalChromeAdapter() {
        imageLitepalAdapter = ImageLitepalAdapter(dataChromeLists,this,this)
        val manager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        main_rv.layoutManager = manager
        main_rv.adapter = imageLitepalAdapter
        Toast.makeText(this, "数据不为空", Toast.LENGTH_SHORT).show()
    }



    private fun initAdapter(id: String, a: String, counts: Int) {

        val map = HashMap<String, String>()
        map["c"] = "WallPaper"
        map["a"] = a
        map["cid"] = id
        map["start"] = "0"
        map["count"] = counts.toString()
        map["from"] = "360chrome"



        chromeManeger.getService(DemoSevice::class.java)
            .queryChrome(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseChrome> {
                override fun onComplete() {
                    Log.d(TAG, "main - onComplete")


                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ResponseChrome) {
//                    Log.d(TAG,t.errmsg + t.data[0].utag + t.data[0].img_1024_768)

                    for (data: ResponseChrome.Data in t.data) {
//                        Log.d(TAG,data.utag )
                    }
                    if (t.errmsg == "正常") {
                        imageLists = t.data as ArrayList<ResponseChrome.Data>

                        //数据保存到数据库中
                        if (dataChromeLists.isNullOrEmpty() && dataChromeLists.size == 0) {
                            imageLists.forEach {
                                if(it.utag.isNotEmpty() && it.class_id.isNotEmpty() && it.img_1024_768.isNotEmpty()) {
                                    val dataChrome =
                                        DataChrome(it.class_id, it.utag, it.img_1024_768)
                                    dataChrome.save()
                                    dataChromeLists.add(dataChrome)
                                }
                            }
                        }
                        LitePal.saveAll(dataChromeLists)

//                    manager = LinearLayoutManager(this@MainActivity)   //线性布局
//                    manager.orientation = LinearLayoutManager.VERTICAL
//                    val gridManeger = GridLayoutManager(this@MainActivity,5)   //网格布局
                        val manager = StaggeredGridLayoutManager(
                            3,
                            StaggeredGridLayoutManager.VERTICAL
                        )  //瀑布流布局

                        main_rv.layoutManager = manager
                        imageAdapter =
                            ImageAdapter(imageLists, this@MainActivity, this@MainActivity)
                        recyclerView.adapter = imageAdapter

                    }


                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "main - onError")
                    Toast.makeText(MainApplication.context,"网络请求失败",Toast.LENGTH_SHORT).show()
                }

            })


        main_tb.setNavigationOnClickListener {
            finish()
        }

    }


    /**
     * 测试单例对象
     */
    private fun testInstance() {

        val rk1 = RetrofitRxKotlin.getInstance()
        val rk2 = RetrofitRxKotlin.getInstance()
        retrofitLists.add(rk1)
        retrofitLists.add(rk2)

        for (re: RetrofitRxKotlin in retrofitLists) {
            Log.i(RetrofitRxKotlin.TAG, "${re.toString()}")
        }
    }

    private fun initImageLoader(context: Context) {

        //1.使用Builder构建 ImageLoader的配置对象
        val config = ImageLoaderConfiguration.Builder(context)
            //加载图片的线程数
            .threadPriority(Thread.NORM_PRIORITY - 2)
            //
            .denyCacheImageMultipleSizesInMemory()
//            .discCacheFileNameGenerator(Md5FileNameGenerator())
            .tasksProcessingOrder(QueueProcessingType.FIFO)
            .writeDebugLogs()
            .build()


        ImageLoader.getInstance().init(config)

        imageLoader.displayImage(url, main_imageview, getSimpleOptions())

    }

    private fun getSimpleOptions(): DisplayImageOptions {

        return DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_launcher_background)
            .showImageForEmptyUri(R.drawable.ic_launcher_foreground)
            .showImageOnFail(R.drawable.ic_launcher_foreground)
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .displayer(RoundedBitmapDisplayer(10))
            .build()
    }


    private fun unused() {
        //        retrofitManager.createRet()   //Retrofit + Call解析User用户

//        retrofitRxManager.createRetrofitRx()    //Retrofit + Rxjava解析User用户


//        retrofitWeaManager.createRetrofit("广州")    //Retrofit + Rxjava解析城市天气


//        RetrofitRxKotlin.getInstance().getService("长沙")  //Retrofit + Rxjava + kotlin解析城市天气


//        testInstance()

        //       RetrofitRxChromeManeger.getInstance().getObserver(this,imageAdapter,main_rv,manager)  //Retrofit + Rxjava + kotlin解析360图片

/*        val thumbnailRequest = Glide.with(this).load(url)
        Glide.with(this).
            load(url)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .thumbnail(thumbnailRequest)
            .into(main_imageview)

 */
    }


}


