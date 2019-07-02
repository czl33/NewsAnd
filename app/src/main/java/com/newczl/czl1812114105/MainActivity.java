package com.newczl.czl1812114105;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.newczl.czl1812114105.Util.PermissionsUtil;
import com.newczl.czl1812114105.fragment.ContentFragment;
import com.newczl.czl1812114105.fragment.LeftMenuFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * 主页面
 *
 */
public class MainActivity extends SlidingFragmentActivity  implements PermissionsUtil.IPermissionsCallback {
    public static final int REQUEST_CODE12 = 0x123;
    public static String TAG_LT_MENU="TAG_LT_MENU";//左菜单的标记
    public static String TAG_CONTENT="TAG_CONTENT";//内容界面的标记
    private SlidingMenu slidingMenu;
    private PermissionsUtil permissionsUtil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (!isTaskRoot()) {
            finish();
            return;
        } else {
            setContentView(R.layout.activity_main);
        }
        slidingMenu=getSlidingMenu();//设置右侧侧边栏
        setBehindContentView(R.layout.leftmenu);//绑定一个滑动布局
        //slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//全屏幕可滑动
        slidingMenu.setMode(SlidingMenu.LEFT);//只能从左边滑动
        //slidingMenu.setBehindOffset(600);//设置预留宽度

        WindowManager windowManager = getWindowManager();
        Point outSize = new Point();
        windowManager.getDefaultDisplay().getSize(outSize);
        int screenWidth = outSize.x;//得到屏幕的宽度

        slidingMenu.setBehindOffset(screenWidth*200/320);//设置预留宽度
        if(PackageManager.PERMISSION_GRANTED!= ContextCompat.checkSelfPermission(this,"android.permission.RECORD_AUDIO"))
        {
            ActivityCompat.requestPermissions(this,new String[]{"android.permission.RECORD_AUDIO"} , 123);
        }
        permissionsUtil = PermissionsUtil
                .with(this)
                .requestCode(0)
                .isDebug(true)
                .permissions(PermissionsUtil.Permission.Camera.CAMERA)
                .request();




        initFragment();//执行初始化Fragment
    }
    public void initFragment(){
        FragmentManager fragmentManager=getSupportFragmentManager();//新建一个Fragment管理器
        FragmentTransaction transaction=fragmentManager.beginTransaction();//开启事务
        transaction.replace(R.id.lf_menu_fragment,new LeftMenuFragment(),TAG_LT_MENU);
        transaction.replace(R.id.content_fragment,new ContentFragment(),TAG_CONTENT);
        transaction.commit();//提交事务
    }

    public LeftMenuFragment getleftMenu() {//得到侧边栏
        FragmentManager fragmentManager=getSupportFragmentManager();//新建一个Fragment管理器
        LeftMenuFragment fragmentByTag = (LeftMenuFragment) fragmentManager.findFragmentByTag(TAG_LT_MENU);
        return fragmentByTag;
    }
    public ContentFragment getContent() {//得到内容栏
        FragmentManager fragmentManager=getSupportFragmentManager();//新建一个Fragment管理器
        ContentFragment fragmentByTag = (ContentFragment) fragmentManager.findFragmentByTag(TAG_CONTENT);
        return fragmentByTag;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 123: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("-------->", "授权请求被允许");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Log.e("-------->", "授权请求被拒绝");
                }
            }
            return;
        }
        permissionsUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //监听跳转到权限设置界面后再回到应用
        permissionsUtil.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE12) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                    if(result!=null) {
                        if (result.contains("www") || result.toLowerCase().contains("http") ) {
                            Intent intent=new Intent(this,NewsDetailActivty.class);
                            if(result.contains("http") || result.contains("Http")){
                                intent.putExtra("url",result);
                            }else{
                                intent.putExtra("url","http://"+result);
                            }
                            startActivity(intent);
                        }
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    @Override
    public void onPermissionsGranted(int requestCode, String... permission) {
        //权限获取回调
    }
    @Override
    public void onPermissionsDenied(int requestCode, String... permission) {
        //权限被拒绝回调
        Toast.makeText(this, "你拒绝了获得摄像机权限", Toast.LENGTH_SHORT).show();
    }


}
