package com.newczl.czl1812114105.fragment;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.newczl.czl1812114105.Base.BasePager;
import com.newczl.czl1812114105.Base.imp.GovPager;
import com.newczl.czl1812114105.Base.imp.homePager;
import com.newczl.czl1812114105.Base.imp.newsPager;
import com.newczl.czl1812114105.Base.imp.settingsPager;
import com.newczl.czl1812114105.Base.imp.smilePager;
import com.newczl.czl1812114105.MainActivity;
import com.newczl.czl1812114105.R;
import com.newczl.czl1812114105.View.NoScrollViewPager;

import java.util.ArrayList;

public class ContentFragment extends BaseFragment {
    private NoScrollViewPager viewPager_content;//得到viewPage
    private BottomNavigationView bottomNavigationView;//找到底部导航栏：
    private ArrayList<BasePager> pagers;//得到所有页面的集合
    @Override
    public View initView() {//初始化View
        View view=View.inflate(mactivity, R.layout.fragment_content,null);
        viewPager_content=view.findViewById(R.id.viewPager_content);
        bottomNavigationView=view.findViewById(R.id.bottomNavigationView);
        return view;
    }

    @Override
    public void initData() {//数据的初始化，将5个页面一一添加入pages中，将Viewpages设置适配器
        pagers=new ArrayList<>();
        pagers.add(new homePager(mactivity));//当前的Activity 传入主页
        pagers.add(new newsPager(mactivity));//传入当前的Activity传入新闻页
        pagers.add(new smilePager(mactivity));//传入当前的Activity传入智慧服务页
        pagers.add(new GovPager(mactivity));//传入当前的Activity传入政务页
        pagers.add(new settingsPager(mactivity));//传入当前的Activity传入设置页
        viewPager_content.setAdapter(new ContentAdapter());//添加适配器
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);//将监听事件设置到底部导航栏上
        viewPager_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                BasePager basePager = pagers.get(i);
                basePager.initData();//初始化数据
                //如果是主页和设置页将不启用侧滑框；
                if(i==1){//智慧页也不需要滑动出来
                    openscroll(true);
                }else{
                    openscroll(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        pagers.get(0).initData();//手动为第一页加载数据
        openscroll(false);//手动为第一页禁用一次滑动

    }

    private void openscroll(boolean b) {
//        获得MainActivity   当前的mActivity其实就是MainActivity
        MainActivity mainActivity= (MainActivity) mactivity;
        SlidingMenu slidingMenu=mainActivity.getSlidingMenu();
        if(b){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else{
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }


    }


    //添加底部导航栏的选择事件
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {//底部导航栏的点击事件
            switch (menuItem.getItemId()){
                case R.id.navigation_home:
                    viewPager_content.setCurrentItem(0,false);//将滑动动画去除
                    return true;
                case R.id.navigation_news:
                    viewPager_content.setCurrentItem(1,false);
                    return true;
                case R.id.navigation_smile:
                    viewPager_content.setCurrentItem(2,false);
                    return true;
                case R.id.navigation_gov:
                    viewPager_content.setCurrentItem(3,false);
                    return true;
                case R.id.navigation_settings:
                    viewPager_content.setCurrentItem(4,false);
                    return true;
            }
            return false;
        }

        };
    //得到新闻中心的页面
    public newsPager getNewsPager() {
        newsPager pager= (newsPager) pagers.get(1);//第二个页面
        return pager;
    }
    //viewpager适配器添加单独的数据
    class ContentAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view==o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            BasePager basePager = pagers.get(position);
            View view=basePager.rootView;//得到当前类中的所维护的一个View对象
            //basePager.initData();//初始化数据，浪费流量 在选中页面时才加载
            container.addView(view);//添加到父容器
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);//移除View
        }
    }
}
