package com.newczl.czl1812114105.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.newczl.czl1812114105.MainActivity;

import cn.jpush.android.api.JPushInterface;


public class PushReceiver extends BroadcastReceiver {
    private static final String TAG ="csd" ;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
           Log.d(TAG, "JPush用户注册成功");

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
           Log.d(TAG, "接受到推送下来的自定义消息");

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
           Log.d(TAG, "接受到推送下来的通知");//在通知到达时触发


            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
           Log.d(TAG, "用户点击打开了通知");//当用户点击时触发

            }


            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {

                //用户点击通知会走的方法

                //获取推送消息的方法
                String content = bundle.getString(JPushInterface.EXTRA_ALERT);

                // 在这里可以自己写代码去定义用户点击后的行为
                if (context != null) {
                    Log.e("content", content);
                    //例如 如果推送内容以【消息】开头 则点击后跳转到消息的Activity 否则跳转到主页面
                    Intent i = new Intent(context, MainActivity.class); //打开主界面
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);

                } else {

                }


            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            } else {
                Log.d("陈", "你点击了通知？");
            }
        } catch (Exception e) {

        }

    }
}
