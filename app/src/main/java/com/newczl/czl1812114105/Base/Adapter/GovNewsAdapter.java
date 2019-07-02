package com.newczl.czl1812114105.Base.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newczl.czl1812114105.Bean.GovNews;
import com.newczl.czl1812114105.NewsDetailActivty;
import com.newczl.czl1812114105.R;

import java.util.ArrayList;
import java.util.List;

public class GovNewsAdapter extends RecyclerView.Adapter {
    private List<GovNews.ShowapiResBodyBean.PagebeanBean.ContentlistBean> data;
    private Context context;//上下文

    public List<GovNews.ShowapiResBodyBean.PagebeanBean.ContentlistBean> getData() {
        return data;
    }

    public void setData(List<GovNews.ShowapiResBodyBean.PagebeanBean.ContentlistBean> data) {
        this.data = data;
    }

    public GovNewsAdapter(List<GovNews.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist) {
        data=contentlist;
    }

    class GovNewsViewHolder extends RecyclerView.ViewHolder{//身体的ViewHolder
        ImageView imageView;
        TextView textView;
        TextView textView1;

        public GovNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.news_img);
            textView=itemView.findViewById(R.id.news_title);
            textView1=itemView.findViewById(R.id.news_time);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context=viewGroup.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_news,null);

        return new GovNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final GovNewsViewHolder govNewsViewHolder= (GovNewsViewHolder) viewHolder;
        final GovNews.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean = data.get(i);
        if(contentlistBean.isHavePic()){
            String url=contentlistBean.getImageurls().get(0).getUrl();
            Glide.with(context).load(url).into(govNewsViewHolder.imageView);
        }else{
            govNewsViewHolder.imageView.setImageResource(R.drawable.pic_item_list_default);
        }
        govNewsViewHolder.textView.setText(contentlistBean.getTitle());
        govNewsViewHolder.textView1.setText(contentlistBean.getPubDate());
        govNewsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NewsDetailActivty.class);
                intent.putExtra("url",contentlistBean.getLink());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
