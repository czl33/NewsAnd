package com.newczl.czl1812114105.Base.Adapter;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newczl.czl1812114105.R;
import com.newczl.czl1812114105.Bean.NewsTabBean;
import com.newczl.czl1812114105.Util.PrefUtil;
import com.newczl.czl1812114105.View.TopViewPager;
import com.viewpagerindicator.CirclePageIndicator;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter{
        public static final int TYPE_HEAD=0;//头部
        public static final int TYPE_BODY=1;//身体
        public static final int TYPE_FOOT=2;//尾部
    private Handler handler;
    private ArrayList<NewsTabBean.TopNews> topnews;//头条新闻
    private ArrayList<NewsTabBean.NewsData> newsList;//新闻列表的数据
    private onItemClickListent onItemClickListent;

    public NewsAdapter.onItemClickListent getOnItemClickListent() {
        return onItemClickListent;
    }

    public void setOnItemClickListent(NewsAdapter.onItemClickListent onItemClickListent) {
        this.onItemClickListent = onItemClickListent;
    }

    public ArrayList<NewsTabBean.TopNews> getTopnews() {
        return topnews;
    }

    public void setTopnews(ArrayList<NewsTabBean.TopNews> topnews) {
        this.topnews = topnews;
    }

    public ArrayList<NewsTabBean.NewsData> getNewsList() {
        return newsList;
    }

    public void setNewsList(ArrayList<NewsTabBean.NewsData> newsList) {
        this.newsList = newsList;
    }

    public NewsAdapter(NewsTabBean newsTabBean) {
        topnews= newsTabBean.data.topnews;
        newsList=newsTabBean.data.news;
    }

    @Override
        public int getItemViewType(int position) {
            if(position==0){
                return TYPE_HEAD;
            }else{
                return TYPE_BODY;
           }
        }

        class NewsBodyViewHolder extends RecyclerView.ViewHolder{//身体的ViewHolder
            ImageView imageView;
            TextView textView;
            TextView textView1;

            public NewsBodyViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView=itemView.findViewById(R.id.news_img);
                textView=itemView.findViewById(R.id.news_title);
                textView1=itemView.findViewById(R.id.news_time);
            }
        }
        class NewsHeaderViewHolder extends RecyclerView.ViewHolder{//头部的ViewHolder
            TopViewPager viewPager;
            TextView tv_title;
            CirclePageIndicator indicator;

            public NewsHeaderViewHolder(@NonNull View itemView) {
                super(itemView);
                viewPager=itemView.findViewById(R.id.page_img);
                tv_title=itemView.findViewById(R.id.tv_title);
                indicator=itemView.findViewById(R.id.circlePageIndicator);
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
            if(type==TYPE_HEAD) {
                View view=View.inflate(viewGroup.getContext(),R.layout.list_item_headr,null);
//                TopViewPager viewPager=view.findViewById(R.id.page_img);
//                final TextView tv_title=view.findViewById(R.id.tv_title);
//                CirclePageIndicator indicator=view.findViewById(R.id.circlePageIndicator);
//                viewPager.setAdapter(new TopNewsAdapter(topnews));
//                indicator.setViewPager(viewPager);//设置ViewPager
//                indicator.setSnap(true);//快照方式，要不要去跟随，默认跟随false
//                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                    @Override
//                    public void onPageScrolled(int i, float v, int i1) {
//
//                    }
//
//                    @Override
//                    public void onPageSelected(int i) {
//                        String title = topnews.get(i).title;//当选中这页时，将标题填入
//                        tv_title.setText(title);
//                    }
//
//                    @Override
//                    public void onPageScrollStateChanged(int i) {
//
//                    }
//                });
//                tv_title.setText(topnews.get(0).title);//手动给第一页添加个标题
//                indicator.onPageSelected(0);//每次初始化位置的时候将指示器置为0；

                return new NewsHeaderViewHolder(view);
            }else{
                View view = View.inflate(viewGroup.getContext(), R.layout.list_item_news, null);
                return new NewsBodyViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
            int type=getItemViewType(i);//获取当前类型
            switch (type){
                case TYPE_BODY:
                    int listsize=i-1;//让数据数组数减掉头部，要不会造成数据越界
                    NewsBodyViewHolder body=(NewsBodyViewHolder)viewHolder;
                    body.textView.setText(newsList.get(listsize).title);
                    //这里绑定数据，如果prefutil能查到id则设置成灰色。
                    String read_ids= PrefUtil.getString(body.itemView.getContext(),"read_ids","");
                    if(read_ids.contains(newsList.get(listsize).id+"")){
                        body.textView.setTextColor(Color.GRAY);
                    }else {
                        body.textView.setTextColor(Color.BLACK);
                    }


                    body.textView1.setText(newsList.get(listsize).pubdate);
                    ImageOptions imageOptions=new ImageOptions.Builder()
                            .setFailureDrawable(body.imageView.getResources().getDrawable(R.drawable.pic_item_list_default))//加载失败的图片
                            .setLoadingDrawable(body.imageView.getResources().getDrawable(R.drawable.pic_item_list_default))//加载中的图片
                            .setUseMemCache(true)//是否缓存
                            .setFadeIn(true)//是否淡入
                            // 加载中或错误图片的ScaleType
                            .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
                            .build();

                    x.image().bind(body.imageView,newsList.get(listsize).listimage,imageOptions);
                    break;
                case TYPE_HEAD:
                    NewsHeaderViewHolder bodysw= (NewsHeaderViewHolder) viewHolder;
                    final TopViewPager viewPager=bodysw.viewPager;
                    final TextView tv_title=bodysw.tv_title;
                    CirclePageIndicator indicator=bodysw.indicator;
                    viewPager.setAdapter(new TopNewsAdapter(topnews));
                    indicator.setViewPager(viewPager);//设置ViewPager
                    indicator.setSnap(true);//快照方式，要不要去跟随，默认跟随false
                    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        String title = topnews.get(i).title;//当选中这页时，将标题填入
                        tv_title.setText(title);
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });
                tv_title.setText(topnews.get(0).title);//手动给第一页添加个标题
                indicator.onPageSelected(0);//每次初始化位置的时候将指示器置为0；
                    //这里去设置轮播
                    if(handler==null){//当handler为空时
                        handler=new Handler(){
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                int cutt=viewPager.getCurrentItem();//得到当前的数
                                cutt++;
                                if(cutt>topnews.size()-1){
                                    cutt=0;
                                }
                                viewPager.setCurrentItem(cutt);
                                handler.sendEmptyMessageDelayed(0,2000);//2秒后发送一个数据
                            }
                        };
                        handler.sendEmptyMessageDelayed(0,2000);//2秒后发送一个数据
                    };

                    viewPager.setOnTouchListener(new View.OnTouchListener() {//触摸事件
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()){
                                case MotionEvent.ACTION_DOWN:
                                    handler.removeMessages(0);
                                    Log.i("onTouch: ", "MotionEvent.ACTION_DOWN:");
                                    break;
                                    case MotionEvent.ACTION_CANCEL://取消事件，移动到其他地方不属于Viewpager的地方
                                        handler.sendEmptyMessageDelayed(0,2000);//2秒后发送一个数据
                                        Log.i("onTouch: ", "MotionEvent.ACTION_CANCEL:");
                                        break;
                                case MotionEvent.ACTION_UP:
                                    handler.sendEmptyMessageDelayed(0,2000);//2秒后发送一个数据
                                    Log.i("onTouch: ", "MotionEvent.ACTION_UP:");
                                    break;
                                default:
                                    break;
                            }
                            return false;
                        }
                    });


                    break;
                case TYPE_FOOT:
                    break;
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListent.onItemClick(v,i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return newsList.size()+1;
        }



        public interface onItemClickListent{//写一个接口
            void onItemClick(View v,int poistion);
        }

    }