package com.newczl.czl1812114105.Base.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.newczl.czl1812114105.R;
import com.newczl.czl1812114105.Bean.NewsTabBean;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

public class TopNewsAdapter extends PagerAdapter {
    private ArrayList<NewsTabBean.TopNews> topnews;

    public TopNewsAdapter(ArrayList<NewsTabBean.TopNews> topnews) {
        this.topnews=topnews;
    }

    @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return o==view;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
            ImageView imageView=new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);//填充满全屏
            ImageOptions imageOptions=new ImageOptions.Builder()
                    .setFailureDrawable(container.getContext().getResources().getDrawable(R.drawable.topnews_item_default))//加载失败的图片
                    .setLoadingDrawable(container.getContext().getResources().getDrawable(R.drawable.topnews_item_default))//加载中的图片
                    .setUseMemCache(true)//是否缓存
                    .setFadeIn(true)//是否淡入
                    // 加载中或错误图片的ScaleType
                    .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
                    .build();
            //imageView.setImageResource(R.drawable.topnews_item_default);
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent=new Intent(container.getContext(), NewsDetailActivty.class);
//                    intent.putExtra("url",topnews.get(position).url);
//                    container.getContext().startActivity(intent);
//                }
//            });//给图片添加点击事件
            String sdurl=topnews.get(position).topimage;//图片下载的链接
            x.image().bind(imageView,sdurl,imageOptions);//将图片绑定上imageView
            //Log.i("topnews", topnews.get(position).toString());
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }