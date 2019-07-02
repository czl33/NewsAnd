package com.newczl.czl1812114105.Base.imp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.newczl.czl1812114105.Base.BasePager;
import com.newczl.czl1812114105.Bean.RobotMsg;
import com.newczl.czl1812114105.MapActivity;
import com.newczl.czl1812114105.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 设置页面
 */
public class settingsPager extends BasePager {
    public settingsPager(Activity activity) {
        super(activity);
    }

    @ViewInject(R.id.mapbutton)
    private Button mapButton;//地图跳转

    @Override
    public void initData() {//动态的添加布局
//        Log.i("settingsPager","我加载了");
//        TextView textView=new TextView(mActivity);//传入上下文对象，也就是一个Activity
//        textView.setText("设置");
//        textView.setTextColor(Color.RED);
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextSize(22);
        View view=View.inflate(mActivity, R.layout.setting,null);
        frameLayout.addView(view);//往层布局中去添加布局
        x.view().inject(this,view);//注入xutils
        title.setText("设置");//头部的名字
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity, MapActivity.class);
                mActivity.startActivity(intent);
            }
        });
    }
}
