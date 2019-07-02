package com.newczl.czl1812114105;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mob.commons.SHARESDK;
import com.newczl.czl1812114105.Util.PrefUtil;
import com.newczl.czl1812114105.View.SlideBackActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsDetailActivty extends SlideBackActivity {
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;//找到工具栏
    @ViewInject(R.id.tvtitle)
    private TextView titles;//找到title标题
    @ViewInject(R.id.progressBar)
    private ProgressBar progressBar;
    @ViewInject(R.id.progressBar2)
    private ProgressBar progressBar2;//找到长条的进度

    @ViewInject(R.id.web_View)
    private WebView web_View;//找到浏览视图
    private String murl;
    private int mTempwhich;//临时选择的字体大小
    private int mTextSizeWhich;//最终选择的字体大小//默认2；
    private String[] items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去掉自带的标题栏
        setContentView(R.layout.activity_news_detail);
        //工具栏做的事情
        x.view().inject(this);
//        为工具栏添加返回按钮
        items=new String[]{"超大号字体","大号字体","正常字体","小号字体","超小号字体"};//单选选项
        int[] size=new int[]{150,120,100,80,50};//对应的字体
        mTextSizeWhich= PrefUtil.getint(this,"TextSize",2);//取出默认的值


        toolbar.setNavigationIcon(R.drawable.ic_back_back_black_24dp);//图标
        toolbar.inflateMenu(R.menu.news_detail_menu);//实例化一个菜单
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {//添加返回点击事件
            @Override
            public void onClick(View v) {
                finish();//结束当前页面
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {//菜单的点击
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.textSize:
//                        Log.i("你", "点击了放大: ");
                        //这边设置字体大小。弄出个弹窗吧
                        showChooseDielog();
                        break;
                    case R.id.share:
//                        Log.i("你", "点击了分享: ");
                        showShareDialog();//调用分享组件的方法
                        break;
                }
                return false;
            }
        });
        murl=getIntent().getStringExtra("url");//获取传入过来的url地址
        web_View.loadUrl(murl);//加载url
        WebSettings settings=web_View.getSettings();
        settings.setJavaScriptEnabled(true);//启用js
        settings.setUseWideViewPort(true);//支持双击放大
        //安卓5.0以后安卓不允许加载非安全的请求
        settings.setBlockNetworkImage(false);//解决图片不显示
        settings.setTextZoom(size[mTextSizeWhich]);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //添加模式，允许同时加载 http 与 https 的请求。
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        web_View.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.i("ha", "onReceivedTitle: "+title);
                titles.setText(title);;
            }
        });
        web_View.setWebViewClient(new WebViewClient(){
            //页面开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //开始加载则显示
                progressBar.setVisibility(View.VISIBLE);
            }
            //页面加载结束了
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //加载结束
                progressBar.setVisibility(View.INVISIBLE);
            }
            //所有链接走的方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //android 7.0系统以上 已经摒弃了shouldOverrideUrlLoading(WebView view, String url)此方法
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                } else {
                    view.loadUrl(request.toString());
                }
                return true;
            }
        });

//        web_View.goBack();//返回
//        web_View.goForward();//前进
//        web_View.setWebChromeClient(new WebChromeClient(){//长条进度条
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                progressBar2.setVisibility(View.VISIBLE);
//                progressBar2.setProgress(newProgress);
//                if(newProgress==100){
//                    progressBar2.setVisibility(View.INVISIBLE);
//                }
//                Log.i("onProgressChanged: ", newProgress+"");
//            }
//
//        });


    }

    private void showChooseDielog() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);//提示框
        mTempwhich=0;
        builder.setTitle("字体设置")
                .setSingleChoiceItems(items,mTextSizeWhich , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTempwhich=which;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Log.i("点击", "onClick: "+which);
                        WebSettings settings = web_View.getSettings();
                        switch (mTempwhich){
                            case 0://超大字体
                                settings.setTextZoom(150);
                                break;
                            case 1://大字体
                                settings.setTextZoom(120);
                                break;
                            case 2://普通字体
                                settings.setTextZoom(100);
                                break;
                            case 3://小号字体
                                settings.setTextZoom(80);
                                break;
                            case 4://超小号字体
                                settings.setTextZoom(50);
                                break;
                        }
                        mTextSizeWhich=mTempwhich;
                        PrefUtil.setint(NewsDetailActivty.this,"TextSize",mTextSizeWhich);//存入字体大小
                    }
                })
                .setNegativeButton("取消",null)
                .setCancelable(false)
                .show();



    }
    public void showShareDialog() {
//        ShareEntity testBean = new ShareEntity("我是标题", "....。");
//        testBean.setUrl(murl); //分享链接
//        testBean.setImgUrl("https://www.baidu.com/img/bd_logo1.png");
//        ShareUtil.showShareDialog(NewsDetailActivty.this, testBean, ShareConstant.REQUEST_CODE);

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle(titles.getText().toString());
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(murl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(titles.getText().toString());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl("https://www.baidu.com/img/bd_logo1.png");
        //oks.setImagePath("https://www.baidu.com/img/bd_logo1.png");

        // 确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(murl);
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("智慧北京:"+titles.getText().toString());
        // 启动分享GUI
        oks.show(this);
    }

}
