package com.newczl.czl1812114105.Base.imp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newczl.czl1812114105.Base.Adapter.GovNewsAdapter;
import com.newczl.czl1812114105.Base.BasePager;
import com.newczl.czl1812114105.Bean.GovNews;
import com.newczl.czl1812114105.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.show.api.ShowApiRequest;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


/**
 * 政务页面
 */
public class GovPager extends BasePager {
    private Handler mHandler=new Handler();
    private Gson gson=new Gson();
    private GovNewsAdapter govNewsAdapter;

    public GovPager(Activity activity) {
        super(activity);
    }

    @ViewInject(R.id.govNewRefresh)
    private SmartRefreshLayout refreshLayout;//刷新框
    @ViewInject(R.id.govNewRecycleView)
    private RecyclerView govNewRecycleView;//recycleView，数据显示框

    private static int pageid=1;
    private static String newTitle="";

    @Override
    public void initData() {//动态的添加布局
//        Log.i("GovPager","我加载了");
//        TextView textView=new TextView(mActivity);//传入上下文对象，也就是一个Activity
//        textView.setText("政务");
//        textView.setTextColor(Color.RED);
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextSize(22);
        View view=View.inflate(mActivity,R.layout.gov_news_pager,null);
        setting.setVisible(true);
        frameLayout.removeAllViews();
        frameLayout.addView(view);//往层布局中去添加布局

        x.view().inject(this,view);//注入

        title.setText("政务新闻");//头部的名字

        getGovNews(true);//去请求一下新闻
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.settings:
                        showDilog();
                        break;
                }
                return false;
            }
        });


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {//刷新回调
                pageid=1;
                getGovNews(true);//去请求服务器数据
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {//刷新回调
                pageid++;//页面加一
                getGovNews(false);//去请求服务器数据
            }
        });

//        toolbar.setNavigationIcon(R.drawable.img_menu);//在有需要唤出侧滑框的添加NavigationIcon
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {//添加点击打开侧边栏事件
//                Log.i("toolbar", "我被点击了啊");
//                openandclosemenu();
//            }
//        });
    }

    public void getGovNews(final boolean getnew){
        if(isNetworkConnected(mActivity)) {


            new Thread() {
                //在新线程中发送网络请求
                public void run() {
                    String appid = "96870";//要替换成自己的
                    String secret = "2e2135376ec24d93910332af76bafc86";//要替换成自己的
                    final String res = new ShowApiRequest("http://route.showapi.com/109-35", appid, secret)
                            .addTextPara("channelId", "5572a109b3cdc86cf39001db")
                            .addTextPara("channelName", "国内最新")
                            .addTextPara("title", newTitle)
                            .addTextPara("page", pageid + "")
                            .addTextPara("needContent", "0")
                            .addTextPara("needHtml", "0")
                            .addTextPara("needAllList", "0")
                            .addTextPara("maxResult", "10")
                            .addTextPara("id", "")
                            .post();
//把返回内容通过handler对象更新到界面
                    mHandler.post(new Thread() {
                        public void run() {
//                        txt.setText(res+"  "+new Date());
                            Log.i("sd", "run: " + res);
                            if (!getnew) {
                                if (res != null) {
//             Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                                    refreshLayout.finishLoadMore(true);//表示没有更多数据了。
                                } else {
                                    refreshLayout.finishLoadMore(false);// 请求失败
                                    Toast.makeText(mActivity, "没有数据了啊", Toast.LENGTH_SHORT).show();
                                }
//                            GovNews govNews = gson.fromJson(res, GovNews.class);
//                            govNewsAdapter = new GovNewsAdapter(govNews.getShowapi_res_body().getPagebean().getContentlist());
//                            govNewRecycleView.setAdapter(govNewsAdapter);
//                            govNewRecycleView.setLayoutManager(new LinearLayoutManager(mActivity));
                                List<GovNews.ShowapiResBodyBean.PagebeanBean.ContentlistBean> olddata = govNewsAdapter.getData();
                                GovNews govNews = gson.fromJson(res, GovNews.class);//新数据
                                List<GovNews.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist = govNews.getShowapi_res_body().getPagebean().getContentlist();
                                olddata.addAll(contentlist);
                                govNewsAdapter.setData(olddata);//将新数据移动到老数据后面
                                govNewsAdapter.notifyDataSetChanged();//通知数据改变
                            } else {
                                if (res != null) {
                                    refreshLayout.finishRefresh(true);//表示没有更多数据了。
                                    GovNews govNews = gson.fromJson(res, GovNews.class);
                                    govNewsAdapter = new GovNewsAdapter(govNews.getShowapi_res_body().getPagebean().getContentlist());
                                    govNewRecycleView.setAdapter(govNewsAdapter);
                                    govNewRecycleView.setLayoutManager(new LinearLayoutManager(mActivity));

                                } else {
                                    refreshLayout.finishRefresh(false);
                                }
//                            List<GovNews.ShowapiResBodyBean.PagebeanBean.ContentlistBean> olddata = govNewsAdapter.getData();
//                            GovNews govNews = gson.fromJson(res, GovNews.class);//新数据
//                            List<GovNews.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist = govNews.getShowapi_res_body().getPagebean().getContentlist();
//                            olddata.addAll(contentlist);
//                            govNewsAdapter.setData(olddata);//将新数据移动到老数据后面
//                            govNewsAdapter.notifyDataSetChanged();//通知数据改变
                            }
                        }
                    });
                }
            }.start();
        }else{
            Toast.makeText(mActivity, "无网络连接", Toast.LENGTH_SHORT).show();
        }
    }


    private void showDilog() {//设置弹框
        AlertDialog.Builder builder=new AlertDialog.Builder(mActivity);//提示框
        final View view=View.inflate(mActivity,R.layout.setting_page,null);
        final AlertDialog alertDialog=builder.setView(view)
                .setPositiveButton("取消",null)
                .setNegativeButton("确定",null)
                .setCancelable(false).create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {//确定该做的事情
            @Override
            public void onClick(View v) {
                TextInputLayout textInputLayout=view.findViewById(R.id.inputLayout);
                String sd=textInputLayout.getEditText().getText().toString();//获得里面的文本哦
                newTitle=sd;
                getGovNews(true);
                alertDialog.dismiss();
            }
        });
    }
    public static boolean isNetworkConnected(Context context) {//获取网络连接
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


}
