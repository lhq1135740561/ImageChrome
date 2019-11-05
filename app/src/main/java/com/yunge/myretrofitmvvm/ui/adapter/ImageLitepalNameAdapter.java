package com.yunge.myretrofitmvvm.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yunge.myretrofitmvvm.R;
import com.yunge.myretrofitmvvm.java.model.DataName;

import java.util.List;

public class ImageLitepalNameAdapter extends RecyclerView.Adapter<ImageLitepalNameAdapter.ViewHolder> {

    private List<DataName> imageNameList;

    private ImageNameAdapter.OnImageNameClickLisenter onClick;

    private Context mContext;


    public ImageLitepalNameAdapter(List<DataName> imageNameList, ImageNameAdapter.OnImageNameClickLisenter onClick, Context context) {
        this.imageNameList = imageNameList;
        this.onClick = onClick;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.image_name,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataName data = imageNameList.get(position);
        holder.image_name.setImageResource(R.drawable.v1);
        holder.text_name.setText(data.getName());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //接口回调的方法
                onClick.OnItemClick(position,data.getPage(),data.getName());

            }
        });
    }

    @Override
    public int getItemCount() {
        return imageNameList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView image_name;
        TextView text_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_name = itemView.findViewById(R.id.image_name);
            text_name = itemView.findViewById(R.id.image_name_text);
            view = itemView;
        }
    }
}
