package com.newczl.czl1812114105.Base.imp.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newczl.czl1812114105.R;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    public ArrayList<Chat> data;

    public ChatAdapter(ArrayList<Chat> data) {
        this.data=data;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();//返回当前的类型
    }

    class ChatViewHolder extends RecyclerView.ViewHolder{//用同一个Viewholder
        TextView textView;
        ImageView imageView;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            //解决布局问题
            itemView.setLayoutParams(layoutParams);
            imageView=itemView.findViewById(R.id.imageView);
            textView=itemView.findViewById(R.id.textView);

        }
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if(i==Chat.ROBOT_CHAT){//根据聊天的类型去实例化一个对话布局
            view=View.inflate(viewGroup.getContext(), R.layout.robot_chat_pager,null);
        }else{
            view=View.inflate(viewGroup.getContext(), R.layout.person_chat_pager,null);
        }
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder viewHolder, int i) {
        Chat chat=data.get(i);
        viewHolder.textView.setText(chat.getMess());
        viewHolder.imageView.setImageResource(chat.getImageIds());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
