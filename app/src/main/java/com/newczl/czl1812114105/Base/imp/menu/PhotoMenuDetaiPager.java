package com.newczl.czl1812114105.Base.imp.menu;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newczl.czl1812114105.Base.Adapter.PhotoMenuAdapter;
import com.newczl.czl1812114105.Base.BaseMenuDetaiPager;
import com.newczl.czl1812114105.R;
import com.newczl.czl1812114105.Bean.PhotosBean;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import static com.newczl.czl1812114105.Global.requestParams.PHOTO_URL;


/**
 *组图菜单详情页
 */
public class PhotoMenuDetaiPager extends BaseMenuDetaiPager implements MenuItem.OnMenuItemClickListener {
    private boolean istrue=true;//判断当前是否是线性布局、默认是
    @ViewInject(R.id.photoRecyclerView)
    private RecyclerView photoRecyclerView;
    private ArrayList<PhotosBean.PhotoNews> mNewsList;//组图的列表

    public PhotoMenuDetaiPager(Activity mActivity, Menu photoMenu) {
        super(mActivity);
        photoMenu.findItem(R.id.toggle).setOnMenuItemClickListener(this);//给传过来的item设置点击事件
    }


    @Override
    public View initView() {
//        TextView view=new TextView(mActivity);
//        view.setText("组图菜单详情页");
//        view.setTextSize(22);
//        view.setTextColor(Color.RED);
//        view.setGravity(Gravity.CENTER);
        View view=View.inflate(mActivity,R.layout.pager_photo_menu_detail,null);
        x.view().inject(this,view);//xutils注入当前View

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getServerData();
    }

    private void getServerData() {
        x.http().get(PHOTO_URL, new Callback.CacheCallback<String>() {
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
                Log.i("JAVA","开始请求");
                if(result !=null) {
                    Log.i("JAVA","缓存中有数据");
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
                    parseJson(result);

                }
            }
        });
    }

    private void parseJson(String result) {
        Gson gson=new Gson();
        PhotosBean photosBean = gson.fromJson(result, PhotosBean.class);//解析出组图的实体类
        mNewsList=photosBean.data.news;//获取图片集合
        photoRecyclerView.setAdapter(new PhotoMenuAdapter(mNewsList));
       photoRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));//默认线性布局

    }

    public boolean onMenuItemClick(MenuItem item) {
        if(istrue){
            item.setIcon(R.drawable.ic_table_24dp);
            photoRecyclerView.setLayoutManager(new GridLayoutManager(mActivity,2));//把布局改为瀑布流
            istrue=false;//改为false
        }else{
            item.setIcon(R.drawable.ic_line_24dp);
            photoRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));//改为线性布局
            istrue=true;//改为true;
        }
        return false;
    }
}
