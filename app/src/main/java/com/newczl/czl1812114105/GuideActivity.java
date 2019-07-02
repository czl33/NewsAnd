package com.newczl.czl1812114105;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.newczl.czl1812114105.Util.PrefUtil;

import java.util.ArrayList;

/**
 * 新手引导页面
 *
 */
public class GuideActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private int[] Images=new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
    private ArrayList<ImageView> data;//imageView的集合
    private LinearLayout linearLayout;//显示白点的布局
    private ImageView pointcheck;//当前选中页的红点
    private int pointleft;//圆点与圆点之间的差距
    private Button btn_start;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        //PrefUtil.setBoolean(this,"is_first",false);//让下次不进入新手帮助页面
        btn_start=findViewById(R.id.btn_start);//找到开始按钮
        linearLayout=findViewById(R.id.linearLayout);//找到线性布局
        pointcheck=findViewById(R.id.pointcheck);//找到这个小红点
        viewPager=findViewById(R.id.viewPager);//找到ViewPager
        initDate();//执行初始化的数据
        viewPager.setAdapter(new GuideAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,float positionOffset, int positionOffsetPixels) {
                //当前位置，
                // positionOffset是当前页面滑动比例，如果页面向右翻动，这个值不断变大，
                // 最后在趋近1的情况后突变为0。如果页面向左翻动，这个值不断变小，最后变为0。
                //positionOffsetPixels是当前页面滑动像素，变化情况和positionOffset一致。
                // Log.i("当前移动位置", " 现在第"+position+"页,偏移量是"+positionOffset+"");
                //计算小红点的滑动值=int（偏移量*圆点与圆点之间的差距）+当前的位置*圆点与圆点之间的距离
                int checkscroll=(int)(positionOffset*pointleft)+position*pointleft;
                FrameLayout.LayoutParams params=(FrameLayout.LayoutParams)pointcheck.getLayoutParams();
                params.leftMargin=checkscroll;
                pointcheck.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int i) {
//                如果到第三个页面则显示按钮
                if(i==2){
                btn_start.setVisibility(View.VISIBLE);
                }else{
                    btn_start.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //找到线性布局
//圆点的间距： 视图没加载完成不能计算
//        int pointleft=linearLayout.getChildAt(1).getLeft()-linearLayout.getChildAt(0).getLeft();
//        Log.i("圆点的间距", "onCreate: "+pointleft);
        //监听视图树的完成.
        pointcheck.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pointcheck.getViewTreeObserver().removeGlobalOnLayoutListener(this);//清楚掉当前的监听事件
                pointleft=linearLayout.getChildAt(1).getLeft()-linearLayout.getChildAt(0).getLeft();//视图加载完成可以计算
                Log.i("圆点的间距", "onCreate: "+pointleft);
            }
        });

    }
    private void initDate(){//初始化当前页面的数据
        data=new ArrayList<>();
        for (int i = 0; i < Images.length ; i++) {
            ImageView view =new ImageView(this);
            view.setBackgroundResource(Images[i]);
            data.add(view);//初始化一个imgView的ArrayList
//            初始化小圆点
            ImageView point=new ImageView(this);//有几张图片生成几个小圆点
            point.setImageResource(R.drawable.point_gray);
            //先初始化圆点的布局参数：params 或得长宽
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if(i>0){//除第一个外添加
                params.leftMargin=50;//设置左外边距为50dp
            }
            point.setLayoutParams(params);
            //加入线性布局
            linearLayout.addView(point);

        }

    }
    class GuideAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view==o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {//初始化imageView对象
            ImageView view=data.get(position);
            container.addView(view);//加入父容器
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
    public void start(View view){//点击按钮的实现。跳转主页
        PrefUtil.setBoolean(this,"is_first",false);//将第一次进入改为false；
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
