package com.newczl.czl1812114105.Base.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newczl.czl1812114105.Bean.PhotosBean;
import com.newczl.czl1812114105.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

public class PhotoMenuAdapter extends RecyclerView.Adapter {
    private ArrayList<PhotosBean.PhotoNews> data;//将组图数据得到

    public PhotoMenuAdapter(ArrayList<PhotosBean.PhotoNews> mNewsList) {
        data=mNewsList;
    }

    class PhotoMenuViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        public PhotoMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            //布局问题，需要单独设置外边距以及匹配父容器
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(25,25,25,25);

            itemView.setLayoutParams(layoutParams);
            textView=itemView.findViewById(R.id.IV_title);
            imageView=itemView.findViewById(R.id.IV_photo);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(viewGroup.getContext(), R.layout.photo_item,null);
        return new PhotoMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PhotoMenuViewHolder viewHolder1= (PhotoMenuViewHolder) viewHolder;//转换一下
        viewHolder1.textView.setText(data.get(i).title);
        ImageOptions imageOptions=new ImageOptions.Builder()
                .setFailureDrawable(viewHolder.itemView.getContext().getResources().getDrawable(R.drawable.topnews_item_default))//加载失败的图片
                .setLoadingDrawable(viewHolder.itemView.getContext().getResources().getDrawable(R.drawable.topnews_item_default))//加载中的图片
                .setUseMemCache(true)//是否缓存
                .setFadeIn(true)//是否淡入
                // 加载中或错误图片的ScaleType
                .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
                .build();
        x.image().bind(viewHolder1.imageView,data.get(i).listimage,imageOptions);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
