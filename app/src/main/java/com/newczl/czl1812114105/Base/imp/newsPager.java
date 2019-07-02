package com.newczl.czl1812114105.Base.imp;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newczl.czl1812114105.Base.BaseMenuDetaiPager;
import com.newczl.czl1812114105.Base.BasePager;
import com.newczl.czl1812114105.Base.imp.menu.InteractMenuDetaiPager;
import com.newczl.czl1812114105.Base.imp.menu.NewsMenuDetaiPager;
import com.newczl.czl1812114105.Base.imp.menu.PhotoMenuDetaiPager;
import com.newczl.czl1812114105.Base.imp.menu.TopicMenuDetaiPager;
import com.newczl.czl1812114105.MainActivity;
import com.newczl.czl1812114105.R;
import com.newczl.czl1812114105.Bean.NewsMenu;
import com.newczl.czl1812114105.fragment.LeftMenuFragment;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 新闻中心页面
 */
public class newsPager extends BasePager {
    private NewsMenu data;
    public newsPager(Activity activity) {
        super(activity);
    }
    private ArrayList<BaseMenuDetaiPager> baseMenuDetaiPagers;

    @Override
    public void initData() {//动态的添加布局
        Log.i("newsPager","我加载了");
//        TextView textView=new TextView(mActivity);//传入上下文对象，也就是一个Activity
//        textView.setText("新闻中心");
//        textView.setTextColor(Color.RED);
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextSize(22);
//        frameLayout.addView(textView);//往层布局中去添加布局
        title.setText("新闻");//头部的名字
        toolbar.setNavigationIcon(R.drawable.img_menu);//在有需要唤出侧滑框的添加NavigationIcon
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//添加点击打开侧边栏事件
                //Log.i("toolbar", "我被点击了啊");
                openandclosemenu();
            }
        });
        getServerData();//从服务器中得到json数据
    }

    private void getServerData() {
        RequestParams requestParams= com.newczl.czl1812114105.Global.requestParams.CATEGORY_URL;
        requestParams.setCacheMaxAge(1000*600);
        x.http().get(requestParams, new Callback.CacheCallback<String>() {
                    private boolean hasError =false;//是否有错误
                    private String result =null;//默认为空
                    @Override
                    public boolean onCache(String result) {//得到缓存数据, 缓存过期后不会进入
                        this.result = result;
                        return false;//true: 信任缓存数据, 不再发起网络请求; false不信任缓存数据,仍然进行请求
                    }

                    @Override
                    public void onSuccess(String result) {//成功时
                        //如果服务返回304或onCache选择了信任缓存,这时result为null
                        Log.i("JAVA","开始请求");
                        if(result !=null) {
                            Log.i("JAVA","缓存中有数据");
                            this.result = result;
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {//错误
                        hasError =true;
                        Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
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
                    public void onFinished() { //结束
                        if(!hasError && result !=null) {
//                            Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                            parseJson(result);
                        }

                    }
                }
//                new Callback.CommonCallback<String>() { //不带缓存的回调
//            @Override
//            public void onSuccess(String result) {//成功！
////                Log.i("onSuccess", result);
//                parseJson(result);
//            }
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                Log.i("onSuccess","我失败了");
//            }
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//            @Override
//            public void onFinished() {
//                Log.i("onSuccess","我结束了");
//            }
//        }
        );
    }
    //解析数据
    private void parseJson(String result) {
        Gson gson=new Gson();//实例化一个Gson对象
        data=gson.fromJson(result, NewsMenu.class);//转成一个类。
        Log.i("data", data.toString());
        MainActivity mainActivity= (MainActivity) mActivity;//找到MainActivty
        LeftMenuFragment leftMenuFragment = mainActivity.getleftMenu();//找到侧边栏
        leftMenuFragment.setMenuData(data.data);
//        LeftMenuFragment.leftMenuAdapter leftMenuAdapter=leftMenuFragment.lMAdapter;  //获取适配器，等需要改变数据应该会用到
//        leftMenuAdapter.notifyDataSetChanged();

        //初始化4个菜单页面
        baseMenuDetaiPagers=new ArrayList<>();
        baseMenuDetaiPagers.add(new NewsMenuDetaiPager(mActivity,data.data.get(0).children));//初始化新闻的时候传入数据（子页面数据）
        baseMenuDetaiPagers.add(new TopicMenuDetaiPager(mActivity));
        baseMenuDetaiPagers.add(new PhotoMenuDetaiPager(mActivity,photoMenu));//这里将标题栏的引用传过去
        baseMenuDetaiPagers.add(new InteractMenuDetaiPager(mActivity));
        setCurrentDetailPager(0);//默认设置为第一页
    }

    public void setCurrentDetailPager(int position) {//修改frameLayout布局
        BaseMenuDetaiPager baseMenuDetaiPager=baseMenuDetaiPagers.get(position);//获取到传进来的位置的那个内容
        //从frameLayout中清除掉所有View
        frameLayout.removeAllViews();
        //加入BaseMenu所管理的那个View
        frameLayout.addView(baseMenuDetaiPager.RootView);
        baseMenuDetaiPager.initData();//初始化数据
        //修改标题栏：
        title.setText(data.data.get(position).title);
        if(baseMenuDetaiPager instanceof PhotoMenuDetaiPager){//如果是组图的话
            phototoggle.setIcon(R.drawable.ic_line_24dp);
            phototoggle.setVisible(true);
        }else{
            //让toolbar的菜单项隐藏
            phototoggle.setVisible(false);
        }
    }
}
