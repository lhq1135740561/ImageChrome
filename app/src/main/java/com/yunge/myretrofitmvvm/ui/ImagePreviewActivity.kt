package com.yunge.myretrofitmvvm.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.meis.widget.photodrag.PhotoDragHelper
import com.yunge.myretrofitmvvm.R
import kotlinx.android.synthetic.main.activity_image_preview.*

class ImagePreviewActivity : FragmentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)

        //获取传值过来的title和image地址
        val title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")

        preview_tb.title = title
        Glide.with(this).load(url).error(R.mipmap.v2).into(preview_image_view)



        preview_content.setDragListener(PhotoDragHelper().setOnDragListener(object : PhotoDragHelper.OnDragListener{

            override fun onAlpha(alpha: Float) {
                preview_content.alpha = alpha
            }

            override fun onAnimationEnd(mSlop: Boolean) {
                if (mSlop){
                    finish()
                    overridePendingTransition(0,0)
                }
            }

            override fun getDragView(): View {
                return preview_image_view
            }
        }))

        preview_tb.setNavigationOnClickListener {
            finish()
        }

        preview_image_view.setOnClickListener {
            finish()
        }
    }
}
