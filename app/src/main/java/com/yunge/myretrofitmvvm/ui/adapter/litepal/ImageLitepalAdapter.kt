package com.yunge.myretrofitmvvm.ui.adapter.litepal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yunge.myretrofitmvvm.R
import com.yunge.myretrofitmvvm.java.model.DataChrome
import com.yunge.myretrofitmvvm.java.model.ResponseChrome.Data
import com.yunge.myretrofitmvvm.ui.adapter.ImageAdapter

class ImageLitepalAdapter(private val imageLists: List<DataChrome>, private var context: Context, private val onClickLister: ImageAdapter.OnClickLister) : RecyclerView.Adapter<ImageLitepalAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image: ImageView = itemView.findViewById(R.id.image)
        var text: TextView = itemView.findViewById(R.id.image_name)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int =  if(imageLists.isNullOrEmpty()) 0 else imageLists.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = imageLists[position]
        Glide.with(context).load(data.img_1024_768).error(R.drawable.v1).into(holder.image)
        holder.text.text = data.utag
//        holder.image.setImageResource(R.mipmap.v1)

        holder.image.setOnClickListener{

            onClickLister.onClick(position,data.utag,data.img_1024_768)  //接口回调，创建点击事件
        }
    }
}