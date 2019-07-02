package com.newczl.czl1812114105.Base.imp.menu;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.newczl.czl1812114105.Base.BaseMenuDetaiPager;
import com.newczl.czl1812114105.MainActivity;
import com.newczl.czl1812114105.R;
import com.newczl.czl1812114105.Bean.NewsMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 *新闻菜单详情页
 */
public class NewsMenuDetaiPager extends BaseMenuDetaiPager {
    @ViewInject(R.id.detail_vp)
    private ViewPager viewPager;//Viewpage页面


    @ViewInject(R.id.tabLayout)
    private TabLayout tabLayout;//tabLayout页面

    @ViewInject(R.id.next_pager)
    private ImageButton imageButton;//点击按钮 ，下一个页面

    ArrayList<NewsMenu.NewsDataChildren> childrendata;//接收子页面Children
    ArrayList<TabDetailPager> tabDetailPagers;//生成多少个页面，
    public NewsMenuDetaiPager(Activity mActivity, ArrayList<NewsMenu.NewsDataChildren> children) {
        super(mActivity);
        this.childrendata=children;
    }
    @Override
    public View initView() {
        View view=View.inflate(mActivity, R.layout.pager_news_menu_detail,null);
        x.view().inject(this,view);//将Xutils注入当前页面，这里应该换成this
        return view;
    }

    @Override
    public void initData() {
        tabDetailPagers=new ArrayList<>();//初始化子视图
        for (int i = 0; i < childrendata.size(); i++) {//循环多少次
            TabDetailPager tab=new TabDetailPager(mActivity,childrendata.get(i));//初始化数据时，需要把当前的Detail子对象传进去
            tabDetailPagers.add(tab);
        }
        viewPager.setAdapter(new NewsMenuDetaulAdpter());
        tabLayout.setupWithViewPager(viewPager);//绑定ViewPager
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {//如果不是第一个则禁用全局滑动
                if(i!=0){
                    openscroll(false);
                }else{
                    openscroll(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {//给按钮添加点击事件
            @Override
            public void onClick(View v) {
                int curr=viewPager.getCurrentItem();//得到当前页面
                curr++;
                viewPager.setCurrentItem(curr);//设置下一页
            }
        });

    }




    //开启关闭全局左侧滑菜单
    private void openscroll(boolean b) {
//        获得MainActivity   当前的mActivity其实就是MainActivity
        MainActivity mainActivity= (MainActivity) mActivity;
        SlidingMenu slidingMenu=mainActivity.getSlidingMenu();
        if(b){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else{
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }

    }
    class NewsMenuDetaulAdpter extends PagerAdapter{

        @Override
        public int getCount() {
            return tabDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view==o;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            NewsMenu.NewsDataChildren newsDataChildren = childrendata.get(position);
            return newsDataChildren.title;//得到page的Title
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            TabDetailPager tabDetailager= tabDetailPagers.get(position);//得到当前tab
            View view=tabDetailager.RootView;//得到维护的一个布局
            container.addView(view);//将布局添加进父容器
            tabDetailager.initData();//初始化个数据

            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

}
