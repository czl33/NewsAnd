package com.newczl.czl1812114105;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.newczl.czl1812114105.Util.PrefUtil;

import java.util.ArrayList;

/**
 * 闪屏页面
 *
 */
public class splashActivity extends AppCompatActivity {
    /**
     * 闪屏动画
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,//无状态栏
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//无标题栏
        setContentView(R.layout.activity_splash);
        //imageView=findViewById(R.id.imageView);不是图片，是整个布局
        ConstraintLayout splash=findViewById(R.id.splash);
        //做旋转，渐变，放大的动画。
        ArrayList<Animator> animators=new ArrayList<>();
        ObjectAnimator rotate=ObjectAnimator.ofFloat(splash,"rotation",180f,0f);//旋转
        ObjectAnimator alpha=ObjectAnimator.ofFloat(splash,"alpha",0.1f,1.0f);//渐变
        ObjectAnimator scaleX=ObjectAnimator.ofFloat(splash,"scaleX",0f,1.0f);//放大
        ObjectAnimator scaleY=ObjectAnimator.ofFloat(splash,"scaleY",0f,1.0f);//放大
        animators.add(rotate);//加入动画
        animators.add(alpha);
        animators.add(scaleX);
        animators.add(scaleY);
        //运行动画
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(500);//持续1s
        animatorSet.playTogether(animators);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束，应该跳转到主页
                //如何第一次进入应当进入新手引导页
                boolean is_first_in = PrefUtil.getBoolean(splashActivity.this,"is_first", true);
                Intent intent;
                if(is_first_in){
                    //进入新手引导
                   intent=new Intent(getApplicationContext(),GuideActivity.class);//进入新手页面

                }else{
                    //主页面
                    intent=new Intent(getApplicationContext(),MainActivity.class);//进入主页面
                }
                startActivity(intent);
                finish();//结束当前闪屏

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
