package com.newczl.czl1812114105.Base.imp.menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newczl.czl1812114105.Base.Adapter.NewsAdapter;
import com.newczl.czl1812114105.Base.BaseMenuDetaiPager;
import com.newczl.czl1812114105.NewsDetailActivty;
import com.newczl.czl1812114105.R;
import com.newczl.czl1812114105.Bean.NewsTabBean;
import com.newczl.czl1812114105.Util.PrefUtil;
import com.newczl.czl1812114105.Bean.NewsMenu;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import static com.newczl.czl1812114105.Global.requestParams.SERVER_URL;

public class TabDetailPager extends BaseMenuDetaiPager{
    private RequestParams url;//当前页面数据的url地址
    private NewsMenu.NewsDataChildren newsData;//单个页签的网络数据

//    @ViewInject(R.id.page_img)
//    private TopViewPager viewPager;//页签的viewPager（头条新闻）
//   /@ViewInject(R.id.circlePageIndicator)
//    private CirclePageIndicator indicator;//头条新闻的指示器
    @ViewInject(R.id.rv_recyclerView)
    private RecyclerView rv_recycleView;//获取底下的新闻列表
//    private ArrayList<NewsTabBean.TopNews> topnews;//获得头条新闻

//    @ViewInject(R.id.tv_title)
//    private TextView tv_title;//找到头条新闻的标题

//    private ArrayList<NewsTabBean.NewsData> newsList;//新闻列表的数据

    private NewsAdapter newsAdapter;//创建新闻列表的适配器

    @ViewInject(R.id.refreshLayout)
    private SmartRefreshLayout refreshLayout;//找到刷新布局（下拉记载更多，上拉刷新）
    private String moreUrl;//更多数据的地址


    public TabDetailPager(Activity mActivity, NewsMenu.NewsDataChildren newsDataChildren) {
        super(mActivity);
        newsData=newsDataChildren;//传入子类进来
        url=new RequestParams(SERVER_URL+newsData.url);
        url.setCacheMaxAge(1000*600);
    }


    @Override
    public View initView() {
        //初始化视图
//        textView=new TextView(mActivity);
//        //textView.setText(newsData.title);//这边为空
//        textView.setTextSize(22);
//        textView.setTextColor(Color.RED);
//        textView.setGravity(Gravity.CENTER);
//        return textView;
        View view=View.inflate(mActivity, R.layout.page_tab_detail,null);
        x.view().inject(this,view);//注入一下Xutils

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                getServerData(); //调用去服务器读取数据的方法
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                if(moreUrl!=null){//如果moreUrl中有数据的话
                //从服务器中获取更多数据
                    getMoreDataforServer();
                }else{
                   // refreshlayout.finishLoadMore(0,true,true);//表示没有更多数据了。
                    refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据
                }
            }
        });

        return view;
    }

    private void getMoreDataforServer() {//加载更多
//        Log.i("getMoreDataforServer: ",moreUrl);
        RequestParams moreUrlParams=new RequestParams(moreUrl);//将url转为RequestParams
        x.http().get(moreUrlParams, new Callback.CommonCallback<String>() {
            private String result =null;//默认为空

            @Override
            public void onSuccess(String result) {
                    this.result = result;
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
                if(result !=null) {
//             Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                    parseJson(result, true);//去解析数据，并传入是加载更多
                    refreshLayout.finishLoadMore(true);//请求成功。
                }else{
                    refreshLayout.finishLoadMore(false);// 请求失败
                }

            }
        });

    }

    @Override
    public void initData() {//初始化数据，数据可以由打开新闻页时传进来数据，
        //textView.setText(newsData.title);
        getServerData();//从服务器中得到json数据

    }

    private void getServerData() {//获得网络数据
        x.http().get(url, new Callback.CacheCallback<String>() {
            private boolean hasError =false;//是否有错误
            private String result =null;//默认为空
            @Override
            public boolean onCache(String result) {
                this.result = result;
                return false;
            }
            @Override
            public void onSuccess(String result) {
                //如果服务返回304或onCache选择了信任缓存,这时result为null
                Log.i("success","开始请求");
                if(result !=null) {
                    Log.i("" +
                            "success","缓存中有数据");
                    this.result = result;
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                hasError =true;
                Toast.makeText(x.app(),"网络错误，请重新检查网络连接。", Toast.LENGTH_LONG).show();
                if(ex instanceof HttpException) {//网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                }
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
                //结束
                if(!hasError && result !=null) {
//                            Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                    refreshLayout.finishRefresh(true);//表示刷新成功
                    parseJson(result,false);
                }else{
                    refreshLayout.finishRefresh(false);//表示刷新失败
                }

            }
        });
    }

    private void parseJson(String result,boolean isMore) {//解析数据
        Gson gson=new Gson();
        NewsTabBean newsTabBean = gson.fromJson(result, NewsTabBean.class);//得到解析的数据
        String moreurl=newsTabBean.data.more;//拿到更多的数据
        if(!TextUtils.isEmpty(moreurl)){
            moreUrl= SERVER_URL+moreurl;
        }else{
            moreUrl=null;
        }
        //列表新闻从这加载
//        newsList = newsTabBean.data.news;
        if(!isMore) {//不是加载更多
            newsAdapter = new NewsAdapter(newsTabBean);
            Log.i("newsTabBean", "" + newsTabBean.data.news.size());
            rv_recycleView.setAdapter(newsAdapter);
            rv_recycleView.setLayoutManager(new LinearLayoutManager(mActivity));
            refreshLayout.resetNoMoreData();
        }else{//是加载更多
            ArrayList<NewsTabBean.NewsData> morenews = newsTabBean.data.news;//第二页的数据
            ArrayList<NewsTabBean.NewsData> yuanban=newsAdapter.getNewsList();//原数据
            yuanban.addAll(morenews);//将第二页数据存入原数据页面
            newsAdapter.setNewsList(yuanban);//设置页面数据
            newsAdapter.notifyDataSetChanged();//通知适配器数据改变
        }
        newsAdapter.setOnItemClickListent(new NewsAdapter.onItemClickListent() {
            @Override
            public void onItemClick(View v, int poistion) {
                int cuont=poistion-1;//去掉头部
                ArrayList<NewsTabBean.NewsData> newss=newsAdapter.getNewsList();//获取到里面的数据
                NewsTabBean.NewsData newsdata= newss.get(cuont);//获取当前位置的数据
                String readIds= PrefUtil.getString(mActivity,"read_ids","");
                if(!readIds.contains(newsdata.id+"")){//不包含这个id
                    readIds=readIds+newsdata.id+",";
                    PrefUtil.setString(mActivity,"read_ids",readIds);
                }
                //改变View中title的颜色
                TextView title=v.findViewById(R.id.news_title);
                title.setTextColor(Color.GRAY);//设置成灰色
                Intent intent=new Intent(mActivity, NewsDetailActivty.class);//进行页面的跳转
                intent.putExtra("url",newsdata.url);//将url传入NewsDetailActivty
                mActivity.startActivity(intent);
            }
        });


    }




    //这是头条新闻的适配器
//    public class TopNewsAdapter extends PagerAdapter{
//
//        @Override
//        public int getCount() {
//            return topnews.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
//            return o==view;
//        }
//
//        @NonNull
//        @Override
//        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//            ImageView imageView=new ImageView(mActivity);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);//填充满全屏
//            ImageOptions imageOptions=new ImageOptions.Builder()
//                    .setFailureDrawable(mActivity.getResources().getDrawable(R.drawable.topnews_item_default))//加载失败的图片
//                    .setLoadingDrawable(mActivity.getResources().getDrawable(R.drawable.topnews_item_default))//加载中的图片
//                    .setUseMemCache(true)//是否缓存
//                    .setFadeIn(true)//是否淡入
//                    // 加载中或错误图片的ScaleType
//                    .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
//                    .build();
//            //imageView.setImageResource(R.drawable.topnews_item_default);
//            String sdurl=topnews.get(position).topimage;//图片下载的链接
//            x.image().bind(imageView,sdurl,imageOptions);//将图片绑定上imageView
//            //Log.i("topnews", topnews.get(position).toString());
//            container.addView(imageView);
//            return imageView;
//        }
//
//        @Override
//        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            container.removeView((View) object);
//        }
//    }
    //新闻列表的适配器

}
