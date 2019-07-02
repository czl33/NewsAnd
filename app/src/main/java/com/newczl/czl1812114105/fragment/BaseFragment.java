package com.newczl.czl1812114105.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newczl.czl1812114105.Base.imp.newsPager;

/**
*   编写Fragment的基类
*
 */
public abstract class BaseFragment extends Fragment {
    public Activity mactivity;//为了以后需要用到上下文使用
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {//fragment创建
        super.onCreate(savedInstanceState);
        mactivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {//布局的初始化
        View view=initView();//初始化Fragment布局数据
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {//当父Activity的oncreated执行好后
        super.onActivityCreated(savedInstanceState);
        initData();
    }
    public abstract View initView();//布局的初始化
    public abstract void initData();//数据的初始化


}
