package com.newczl.czl1812114105.Base;

import android.app.Activity;
import android.view.View;

public abstract class BaseMenuDetaiPager {
    public Activity mActivity;
    public View RootView;

    public BaseMenuDetaiPager(Activity mActivity) {
        this.mActivity = mActivity;
        RootView=initView();//维护的一个view对象
    }
    public abstract View initView();//初始化视图，需要由子类去实现
    public void initData(){ };//初始化数据
}
