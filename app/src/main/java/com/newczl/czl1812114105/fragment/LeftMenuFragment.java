package com.newczl.czl1812114105.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.newczl.czl1812114105.Base.imp.newsPager;
import com.newczl.czl1812114105.fragment.Adapter.leftMenuAdapter;
import com.newczl.czl1812114105.MainActivity;
import com.newczl.czl1812114105.R;
import com.newczl.czl1812114105.Bean.NewsMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

public class LeftMenuFragment extends BaseFragment {
    @ViewInject(R.id.MenuDataView)
    private  RecyclerView recyclerView;
    public leftMenuAdapter lMAdapter;

    @Override
    public View initView() {
        View view=View.inflate(mactivity, R.layout.fragment_leftmenu,null);//实例化视图，加载左边布局
        //RecyclerView recyclerView=view.findViewById(R.id.MenuDataView);//1.找到recycleView。
        x.view().inject(this,view);//2.注入xutil的View

        return view;
    }

    @Override
    public void initData() {

    }

    public void setMenuData(ArrayList<NewsMenu.NewsMenuData> data) {//将NewsMenuData对象传过来进行更新页面
        lMAdapter=new leftMenuAdapter(data);
        recyclerView.setAdapter(lMAdapter);//存入数据
        lMAdapter.setOnItemClickLitener(new leftMenuAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(mactivity, "点击的是" + position + "的itemClick", Toast.LENGTH_SHORT).show();
                lMAdapter.setmCurrentPos(position);//将选中的值改为当前位置;
                lMAdapter.notifyDataSetChanged();//通知数据改变
                //状态栏收起：
                //写一个可收可放的方法
                openandclosemenu();
                //替换news页面（侧边栏LeftMenuFragment）内容为位置内容
                setCurrentDetailPager(position);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mactivity));//设置布局管理器
    }
    //该方法为：传入一个位置，替换掉新闻中心的内容
    private void setCurrentDetailPager(int position) {
        //d得到MainActivity，然后获得内容页
        MainActivity mainActivity= (MainActivity) mactivity;
        ContentFragment content = mainActivity.getContent();
        //在通过内容页。获得新闻中心页面
        newsPager np=content.getNewsPager();
        //修改新闻中心的FrameLayout布局
        np.setCurrentDetailPager(position);
    }

    public void openandclosemenu(){//状态栏的开启和关闭
        MainActivity mainActivity= (MainActivity) mactivity;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        slidingMenu.toggle();//开关自动判断
    }

//    public class leftMenuAdapter extends RecyclerView.Adapter<leftMenuAdapter.leftMenuViewHolder>{ //写左边菜单的适配器
//        private ArrayList<NewsMenu.NewsMenuData> data;//传进来的数据
//        public leftMenuAdapter(ArrayList<NewsMenu.NewsMenuData> data) {
//            this.data = data;
//        }
//        class leftMenuViewHolder extends RecyclerView.ViewHolder{
//            TextView textView;
//            public leftMenuViewHolder(@NonNull View itemView) {
//                super(itemView);
//                textView=itemView.findViewById(R.id.item_tv);
//            }
//        }
//
//        @NonNull
//        @Override
//        public leftMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//            View view= LayoutInflater.from(mactivity).inflate(R.layout.item_left_menu,viewGroup,false);
//            return new leftMenuViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull leftMenuViewHolder viewHolder, int i) {
//            TextView textView=viewHolder.textView;
//
//            NewsMenu.NewsMenuData newsMenuData = data.get(i);
//            if(i==mCurrentPos){
//                textView.setEnabled(true);
//            }else{
//                textView.setEnabled(false);
//            }
//            textView.setText(newsMenuData.title);
//        }
//
//        @Override
//        public int getItemCount() {
//            return data.size();
//        }
//    }
}
