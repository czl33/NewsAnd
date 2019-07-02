package com.newczl.czl1812114105;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;


//import com.scwang.smartrefresh.header.WaterDropHeader;


import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.header.FunGameHitBlockHeader;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.xutils.x;

import java.text.SimpleDateFormat;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;


public class MyApp extends Application {

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context,@NonNull RefreshLayout layout) {
                ClassicsHeader.REFRESH_HEADER_PULLING = "下拉可以刷新";
                ClassicsHeader.REFRESH_HEADER_REFRESHING = "正在刷新...";
                ClassicsHeader.REFRESH_HEADER_LOADING = "正在加载...";
                ClassicsHeader.REFRESH_HEADER_RELEASE = "释放立即刷新";
                ClassicsHeader.REFRESH_HEADER_FINISH = "刷新完成";
                ClassicsHeader.REFRESH_HEADER_FAILED = "刷新失败";
                layout.setPrimaryColorsId( android.R.color.white,R.color.colorPrimaryDark);//全局设置主题颜色
                return new ClassicsHeader(context).setTimeFormat(new SimpleDateFormat("上次更新 yyyy-MM-dd HH:mm"));//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context,@NonNull RefreshLayout layout) {
                ClassicsFooter.REFRESH_FOOTER_PULLING = "上拉加载更多";
                ClassicsFooter.REFRESH_FOOTER_RELEASE = "释放立即加载";
                ClassicsFooter.REFRESH_FOOTER_REFRESHING = "正在刷新...";
                ClassicsFooter.REFRESH_FOOTER_LOADING = "正在加载...";
                ClassicsFooter.REFRESH_FOOTER_FINISH = "加载完成";
                ClassicsFooter.REFRESH_FOOTER_FAILED = "加载失败";
                ClassicsFooter.REFRESH_FOOTER_NOTHING = "没有更多数据了";
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20).setPrimaryColorId(android.R.color.white)
                        .setAccentColorId( R.color.colorPrimaryDark);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);//声明一个全局的xutil对象，初始化一个
        x.Ext.setDebug(false);//输出debug日志，开启会影响性能
        JPushInterface.setDebugMode(true);//极光推送初始化
        JPushInterface.init(this);//极光推送初始化
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5ce68b61");//这是讯飞科大的初始化
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        ZXingLibrary.initDisplayOpinion(this);//二维码扫描开启

    }
}
