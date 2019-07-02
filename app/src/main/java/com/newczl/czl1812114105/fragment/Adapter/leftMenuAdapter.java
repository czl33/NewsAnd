package com.newczl.czl1812114105.fragment.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newczl.czl1812114105.R;
import com.newczl.czl1812114105.Bean.NewsMenu;

import java.util.ArrayList;

public class leftMenuAdapter extends RecyclerView.Adapter<leftMenuAdapter.leftMenuViewHolder>{ //写左边菜单的适配器
        private ArrayList<NewsMenu.NewsMenuData> data;//传进来的数据
        private int mCurrentPos;//默认为0被选中

    public void setmCurrentPos(int mCurrentPos) {//添加一个设置选中的值
        this.mCurrentPos = mCurrentPos;
    }

    public static interface OnItemClickLitener {//项的点击监听
            void onItemClick(int position);
        }
        private OnItemClickLitener mOnItemClickLitener;

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;

        }

        public leftMenuAdapter(ArrayList<NewsMenu.NewsMenuData> data) {
            this.data = data;
        }
        class leftMenuViewHolder extends RecyclerView.ViewHolder{
            TextView textView;
            public leftMenuViewHolder(@NonNull final View itemView) {
                super(itemView);
                textView=itemView.findViewById(R.id.item_tv);
            }
        }

        @NonNull
        @Override
        public leftMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_left_menu,viewGroup,false);
            return new leftMenuViewHolder(view);
        }


    @Override
        public void onBindViewHolder(@NonNull leftMenuViewHolder viewHolder, final int i) {
            final TextView textView=viewHolder.textView;

            NewsMenu.NewsMenuData newsMenuData = data.get(i);
            if(i==mCurrentPos){
                textView.setEnabled(true);//设置红色
            }else{
                textView.setEnabled(false);//设置白色
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickLitener != null)
                        mOnItemClickLitener.onItemClick(i);
                }
            });

            textView.setText(newsMenuData.title);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }