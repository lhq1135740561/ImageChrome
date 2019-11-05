package com.yunge.myretrofitmvvm.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yunge.myretrofitmvvm.R
import com.yunge.myretrofitmvvm.java.model.ResponseChrome.Data

class ImageAdapter(private val imageLists: List<Data>,private var context: Context,private val onClickLister: OnClickLister) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    interface OnClickLister{

        fun onClick(position: Int,title: String,url: String)
    }

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
        Glide.with(context).load(data.img_1024_768).error(R.drawable.ic_launcher_background).into(holder.image)
        holder.text.text = data.utag
//        holder.image.setImageResource(R.mipmap.v1)

        holder.image.setOnClickListener{

            onClickLister.onClick(position,data.utag,data.img_1024_768)  //接口回调，创建点击事件
        }
    }
}