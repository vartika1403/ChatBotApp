package com.example.vartikasharma.chatbotapp;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder>{

    private List<MessageChat> messageChatList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView messageSenderTextView;
        ImageView messageSenderImageView;
        TextView messageReceiverTextView;
        LinearLayout leftSide;
        LinearLayout rightSide;

        public MyViewHolder(View view) {
            super(view);
            leftSide = (LinearLayout) view.findViewById(R.id.left_side);
            rightSide = (LinearLayout) view.findViewById(R.id.right_side);
            messageSenderTextView = (TextView) view.findViewById(R.id.sender_text_message);
            messageSenderImageView = (ImageView) view.findViewById(R.id.sender_profile_image);
            messageReceiverTextView = (TextView) view.findViewById(R.id.receiver_text_message);
        }
    }


    public ChatAdapter(List<MessageChat> messageChatList) {
        this.messageChatList = messageChatList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message_right, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MessageChat messageChat = messageChatList.get(position);
        holder.messageSenderTextView.invalidate();
        holder.messageSenderTextView.setText(messageChat.getText());
    }

    @Override
    public int getItemCount() {
        return  messageChatList.size();
    }
}
