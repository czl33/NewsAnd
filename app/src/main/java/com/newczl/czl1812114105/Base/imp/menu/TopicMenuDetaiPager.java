package com.newczl.czl1812114105.Base.imp.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.newczl.czl1812114105.Base.BaseMenuDetaiPager;

/**
 *专题菜单详情页
 */
public class TopicMenuDetaiPager extends BaseMenuDetaiPager {

    public TopicMenuDetaiPager(Activity mActivity) {
        super(mActivity);

    }

    @Override
    public View initView() {
        TextView view=new TextView(mActivity);
        view.setText("专题菜单详情页");
        view.setTextSize(22);
        view.setTextColor(Color.RED);
        view.setGravity(Gravity.CENTER);
        return view;
    }
}
