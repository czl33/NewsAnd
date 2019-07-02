package com.newczl.czl1812114105.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 头条新闻的ViewPager
 */
public class TopViewPager extends ViewPager {
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public TopViewPager(@NonNull Context context) {
        super(context);
    }

    public TopViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    //事件的拦截
    //向上滑动拦截
    //向右滑动并且是第一个，需要拦截
    //向左滑动是最后一个，需要拦截
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);//请求所有父类祖先不拦截我
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX=(int)ev.getX();
                startY=(int)ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                endX=(int)ev.getX();
                endY=(int)ev.getY();
                int dx=endX-startX;
                int dy=endY-startY;
                if(Math.abs(dy)<Math.abs(dx)){
                    int curr=getCurrentItem();//得到现在在第几个了
                    //左右滑动
                    if(dx>0){
                        //右边滑动
                        if(curr==0){
                            getParent().requestDisallowInterceptTouchEvent(false);//拦截
                        }

                    }else{
                        //左边滑动
                        int count=getAdapter().getCount()-1;
                        if(curr==count){
                            getParent().requestDisallowInterceptTouchEvent(false);//拦截
                        }
                    }
                }else{
                    //上下滑动
                    getParent().requestDisallowInterceptTouchEvent(false);//拦截
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
