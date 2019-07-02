package com.newczl.czl1812114105.View;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;


public class SlideBackActivity extends AppCompatActivity {
    private int x1;
    private int y1;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {//重写事件分发
        switch (ev.getAction()){//获取当前的动作
            case MotionEvent.ACTION_DOWN://按下
                x1= (int) ev.getX();//获取当前手指的坐标
                y1= (int) ev.getY();
                break;
            case MotionEvent.ACTION_UP://手拿起来
                int x2= (int) ev.getX();//获取手拿起来时候的坐标
                int y2= (int) ev.getY();
                int dx=x1-x2;
                int dy=y1-y2;
                if(Math.abs(dx)>Math.abs(dy)){
                    if(dx<0){//左边滑动
                        Log.i("左边滑动", dx+"");
                        if(Math.abs(dx)>350){//如果偏移量大于350就finish掉
                            finish();
                        }
                    }else{//右边滑动(不管)
                        Log.i("右边滑动", dx+"");
                    }
                }
                break;

        }
        return super.dispatchTouchEvent(ev);
    }
}
