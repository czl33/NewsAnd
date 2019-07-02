package com.newczl.czl1812114105.Base.imp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.newczl.czl1812114105.Base.BasePager;
import com.newczl.czl1812114105.Base.imp.chat.Chat;
import com.newczl.czl1812114105.Base.imp.chat.ChatAdapter;
import com.newczl.czl1812114105.Bean.RobotMsg;
import com.newczl.czl1812114105.Bean.sendRobot;
import com.newczl.czl1812114105.R;
import com.newczl.czl1812114105.Util.PrefUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 智慧服务页面
 */
public class smilePager extends BasePager {
    @ViewInject(R.id.chat_content)
    private RecyclerView recyclerView;//聊天内容框



    @ViewInject(R.id.speak)
    private Button button;//点击收听语音

    private ArrayList<Chat> data=new ArrayList<>();
    private ChatAdapter chatAdapter;//适配器，用于通知数据集合改变

    private static String[] voide={"xiaoyan","xiaoyu","vixy","vixq","vixf","vixm","xiaoqian"};//人声列表
    private static String avoice;//默认人声
    private static int checkav;//选择人声位置
    private static int speed;//速度
    private static int volume;//音量


    public smilePager(Activity activity) {
        super(activity);
    }
    @Override
    public void initData() {//动态的添加布局
        Log.i("smilePager","我加载了");

            speed=PrefUtil.getint(mActivity,"speed",50);
            checkav=PrefUtil.getint(mActivity,"checkav",0);
            volume=PrefUtil.getint(mActivity,"volume",50);
            avoice=voide[checkav];

//        TextView textView=new TextView(mActivity);//传入上下文对象，也就是一个Activity
//        textView.setText("智慧服务");
//        textView.setTextColor(Color.RED);
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextSize(22);
        View view=View.inflate(mActivity,R.layout.smile_pager_robot,null);
        x.view().inject(this,view);
        frameLayout.removeAllViews();//在这清除掉所有布局
        frameLayout.addView(view);//往层布局中去添加布局
        title.setText("智慧服务");//头部的名字
        setting.setVisible(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.settings:
                        dislog();
                        break;
                }
                return false;
            }
        });
//        toolbar.setNavigationIcon(R.drawable.img_menu);//在有需要唤出侧滑框的添加NavigationIcon
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {//添加点击打开侧边栏事件
//                Log.i("toolbar", "我被点击了啊");
//                openandclosemenu();
//            }
//        });

        //聊天机器人：
        if(data.size()==0){//数据初始化
            data.add(new Chat(R.drawable.robot,"你好啊",Chat.ROBOT_CHAT));
        }
        chatAdapter=new ChatAdapter(data);
        recyclerView.setAdapter(chatAdapter);//添加适配器
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));//添加布局管理器

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSpeech(mActivity);
            }
        });
        //一初始化数据就移动到最后一段
        recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
    }



    //实例化语音组件
    //    /**
//     * 初始化语音识别
//     */
    public void initSpeech(final Context context) {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(context, null);
        //2.设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        //3.设置回调接口
        mDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                if (!isLast) {
                    //解析语音
                    //返回的result为识别后的汉字,直接赋值到TextView上即可
                    //语音的回调函数
                    String result = parseVoice(recognizerResult.getResultString());
                    Log.i("shi", "onResult: "+result);
                    //title.setText(result);
                    data.add(new Chat(R.drawable.person,result,Chat.SELT_CHAT));
                    chatAdapter.notifyDataSetChanged();//通知改变
                    recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);//移动到最后一项


                    //这里将得到语音通知后，去发起请求参数为当前语音解析值，
                    //通过传回的数据一并显示在data集合中
                    //发送result
                    send(result);



                }
            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });
        //4.显示dialog，接收语音输入
        mDialog.show();
        TextView tv_textlink = (TextView) mDialog.getWindow().getDecorView().findViewWithTag("textlink");
        tv_textlink.setText("智慧北京");//设置文本哦
        tv_textlink.getPaint().setFlags(Paint.SUBPIXEL_TEXT_FLAG);//取消下划线
    }

    /**
     * 解析语音json
     */
    public String parseVoice(String resultString) {
        Gson gson = new Gson();
        Voice voiceBean = gson.fromJson(resultString, Voice.class);

        StringBuffer sb = new StringBuffer();
        ArrayList<Voice.WSBean> ws = voiceBean.ws;
        for (Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }
    /**
     * 语音对象封装
     */
    public class Voice {

        public ArrayList<WSBean> ws;

        public class WSBean {
            public ArrayList<CWBean> cw;
        }

        public class CWBean {
            public String w;
        }
    }
    /*
    * 像api提交信息
    */

    private void send(String result) {
        //将提交的信息封装好Json，这里用Gson
        Gson gson=new Gson();
        sendRobot sendRobot=new sendRobot(new sendRobot.Perception(result));//这里直接就将result传入就好
        RequestParams params=new RequestParams("http://openapi.tuling123.com/openapi/api/v2");//由于是http的链接，安卓限制请求，所以我们得去
        //配置一下允许访问http
        params.setAsJsonContent(true);
        params.setBodyContent(gson.toJson(sendRobot));

        x.http().post(params, new Callback.CommonCallback<String>() {
            public String result;
            @Override
            public void onSuccess(String result) {
                this.result=result;
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                displayText(result);
            }
        });




    }

    private void displayText(String result) {
        Gson gson=new Gson();
        RobotMsg robotMsg = gson.fromJson(result, RobotMsg.class);
        speak(robotMsg.results.get(0).values.text);
        data.add(new Chat(R.drawable.robot,robotMsg.results.get(0).values.text,Chat.ROBOT_CHAT));//将解析后的文本数据添加入聊天内容中
        chatAdapter.notifyDataSetChanged();//通知改变
        recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);//移动到最后一项
    }


    //添加说话事件

    private void speak(String text){
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer( mActivity, null);
//2.合成参数设置，详见《 MSC Reference Manual》 SpeechSynthesizer 类
//设置发音人（更多在线发音人，用户可参见 附录 13.2
        mTts.setParameter(SpeechConstant. VOICE_NAME, avoice ); // 设置发音人
        mTts.setParameter(SpeechConstant. SPEED, ""+speed );// 设置语速
        mTts.setParameter(SpeechConstant. VOLUME, ""+volume );// 设置音量，范围 0~100
        mTts.setParameter(SpeechConstant. ENGINE_TYPE, SpeechConstant. TYPE_CLOUD); //设置云端
//设置合成音频保存位置（可自定义保存位置），保存在 “./sdcard/iflytek.pcm”
//保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
//仅支持保存为 pcm 和 wav 格式， 如果不需要保存合成音频，注释该行代码
        //mTts.setParameter(SpeechConstant. TTS_AUDIO_PATH, "./sdcard/iflytek.pcm" );
//3.开始合成
        mTts.startSpeaking(text, new MySynthesizerListener()) ;
    }
    class MySynthesizerListener implements SynthesizerListener {

        @Override
        public void onSpeakBegin() {
            showTip(" 开始播放 ");
        }

        @Override
        public void onSpeakPaused() {
            showTip(" 暂停播放 ");
        }

        @Override
        public void onSpeakResumed() {
            showTip(" 继续播放 ");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos ,
                                     String info) {
            // 合成进度
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                showTip("播放完成 ");
            } else if (error != null ) {
                showTip(error.getPlainDescription( true));
            }
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }


    }

    private void showTip(String plainDescription) {
        Log.i("asdsad", "showTip: "+plainDescription);
    }

    private void dislog() {//提示框弹出
        AlertDialog.Builder builder=new AlertDialog.Builder(mActivity);//提示框
        final View view=View.inflate(mActivity,R.layout.smile_setting_page,null);
//        private static int checkav;//选择人声位置
//        private static int speed;//速度
//        private static int volume;//音量
        final Spinner spinner=view.findViewById(R.id.spinner);//获取选择框
        spinner.setSelection(PrefUtil.getint(mActivity,"checkav",0));//获得各个的位置
        final SeekBar seekBar=view.findViewById(R.id.seekBar3);//音量
        seekBar.setProgress(PrefUtil.getint(mActivity,"volume",50));
        final SeekBar seekBar1=view.findViewById(R.id.seekBar4);//语速
        seekBar1.setProgress(PrefUtil.getint(mActivity,"speed",50));
        final AlertDialog alertDialog=builder.setView(view)
                .setPositiveButton("取消",null)
                .setNegativeButton("确定",null)
                .setCancelable(false).create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {//确定该做的事情
            @Override
            public void onClick(View v) {
                //Toast.makeText(mActivity,
//                    spinner.getSelectedItemPosition()+"-"+seekBar.getProgress()+"-"+seekBar1.getProgress(),
//                    Toast.LENGTH_SHORT).show();
                    PrefUtil.setint(mActivity,"checkav",spinner.getSelectedItemPosition());
                    PrefUtil.setint(mActivity,"volume",seekBar.getProgress());
                    PrefUtil.setint(mActivity,"speed",seekBar1.getProgress());
                    avoice=voide[spinner.getSelectedItemPosition()];//设置
                    volume=seekBar.getProgress();//设置
                    speed=seekBar1.getProgress();//设置
//                private static String avoice=voide[0]; //默认人声
//                private static int speed=50;//速度
//                private static int volume=50;//音量
                    alertDialog.dismiss();
            }
        });

    }


}
