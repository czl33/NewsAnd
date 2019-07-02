package com.newczl.czl1812114105.Base;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.newczl.czl1812114105.MainActivity;
import com.newczl.czl1812114105.R;

/**
 * 基页类
 *
 */
public class BasePager {
    public Activity mActivity;//当前上下文对象，这个就是MainActivity
    public FrameLayout frameLayout;//找到层布局
    public TextView title;//找到title文本
    public Toolbar toolbar;//找到工具栏（菜单按钮）
    public Menu photoMenu;//组图菜单
    public MenuItem phototoggle;//组图菜单下的控件切换插件
    public MenuItem scan;//扫一扫菜单按钮
    public MenuItem setting;


    public View rootView;//当前页面的布局对象


    public BasePager(Activity activity){
        mActivity=activity;//传入当前activity
        rootView=initView();
    }

    public View initView(){//布局的初始化
        View view=View.inflate(mActivity, R.layout.base_pager,null);
        frameLayout=view.findViewById(R.id.fl_content);
        title=view.findViewById(R.id.tvtitle);
        toolbar=view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.phtot_menu);//先在这里实例化菜单
        photoMenu=toolbar.getMenu();
        phototoggle=photoMenu.findItem(R.id.toggle);
        scan=photoMenu.findItem(R.id.scan);//找到扫一扫按钮
        setting=photoMenu.findItem(R.id.settings);//设置页面显示数据
        scan.setVisible(false);//默认不显示，到主页了就显示它。
        phototoggle.setVisible(false);//默认不显示
        setting.setVisible(false);//默认不显示

        return view;
    }
    public  void initData(){//数据的初始化
    }
    public void openandclosemenu(){//状态栏的开启和关闭
        MainActivity mainActivity= (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        slidingMenu.toggle();//开关自动判断
    }
}
