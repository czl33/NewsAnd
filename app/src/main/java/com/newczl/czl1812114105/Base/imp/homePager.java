package com.newczl.czl1812114105.Base.imp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.newczl.czl1812114105.Base.BasePager;
import com.newczl.czl1812114105.MainActivity;
import com.newczl.czl1812114105.R;
import com.uuzuche.lib_zxing.activity.CaptureActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;


/**
 * 首页页面
 */
public class homePager extends BasePager {

    public homePager(Activity activity) {
        super(activity);

    }

    @Override
    public void initData() {//动态的添加布局
        Log.i("homePager","我加载了");
//        TextView textView=new TextView(mActivity);//传入上下文对象，也就是一个Activity
//        textView.setText("主页");
//        textView.setTextColor(Color.RED);
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextSize(22);
        //View view=View.inflate(mActivity, R.layout.home_page,null);
       View view=View.inflate(mActivity,R.layout.home_page,null);
       scan.setVisible(true);//显示扫一扫按钮
        frameLayout.addView(view);//往层布局中去添加布局
        x.view().inject(this,view);
        title.setText("首页");//头部的名字
//       TextView textView=view.findViewById(R.id.button);
//       textView.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               initSpeech(mActivity);
//           }
//       });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {//添加扫一扫的点击事件
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.scan://扫一扫
                        Toast.makeText(mActivity, "你点击了我哦", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mActivity, CaptureActivity.class);
                        mActivity.startActivityForResult(intent, MainActivity.REQUEST_CODE12);
                        break;
                }
                return false;
            }
        });

    }



    //kaishi
//    /**
//     * 初始化语音识别
//     */
//    public void initSpeech(final Context context) {
//        //1.创建RecognizerDialog对象
//        RecognizerDialog mDialog = new RecognizerDialog(context, null);
//        //2.设置accent、language等参数
//        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
//        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
//        //3.设置回调接口
//        mDialog.setListener(new RecognizerDialogListener() {
//            @Override
//            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
//                if (!isLast) {
//                    //解析语音
//                    //返回的result为识别后的汉字,直接赋值到TextView上即可
//                    String result = parseVoice(recognizerResult.getResultString());
//                    Log.i("shi", "onResult: "+result);
//                    //title.setText(result);
//                }
//            }
//
//            @Override
//            public void onError(SpeechError speechError) {
//
//            }
//        });
//        //4.显示dialog，接收语音输入
//        mDialog.show();
//        TextView tv_textlink = (TextView) mDialog.getWindow().getDecorView().findViewWithTag("textlink");
//        tv_textlink.setText("该功能很棒");//设置文本哦
//        tv_textlink.getPaint().setFlags(Paint.SUBPIXEL_TEXT_FLAG);//取消下划线
//    }
//
//    /**
//     * 解析语音json
//     */
//    public String parseVoice(String resultString) {
//        Gson gson = new Gson();
//        Voice voiceBean = gson.fromJson(resultString, Voice.class);
//
//        StringBuffer sb = new StringBuffer();
//        ArrayList<Voice.WSBean> ws = voiceBean.ws;
//        for (Voice.WSBean wsBean : ws) {
//            String word = wsBean.cw.get(0).w;
//            sb.append(word);
//        }
//        return sb.toString();
//    }
//    /**
//     * 语音对象封装
//     */
//    public class Voice {
//
//        public ArrayList<WSBean> ws;
//
//        public class WSBean {
//            public ArrayList<CWBean> cw;
//        }
//
//        public class CWBean {
//            public String w;
//        }
//    }

}
