package com.example.vartikasharma.chatbotapp;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private static String LOG_TAG = ChatAdapter.class.getSimpleName();
    private List<MessageChat> messageChatList;

    public ChatAdapter(List<MessageChat> messageChatList) {
        this.messageChatList = messageChatList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MessageChat messageChat = messageChatList.get(position);
        if (!messageChat.getText().isEmpty() && position >= 0 && position != position - 1) {
            if (messageChat.isRightSide()) {
                holder.messageSenderTextView.invalidate();
                holder.messageReceiverTextView.invalidate();
                holder.messageReceiverTextView.setText("");
                holder.messageReceiverTextView.setBackgroundResource(0);
                holder.messageSenderTextView.setBackgroundResource(R.drawable.text_message_background);
                holder.messageSenderTextView.setText(messageChat.getText());
            } else {
                holder.messageReceiverTextView.invalidate();
                holder.messageSenderTextView.invalidate();
                holder.messageSenderTextView.setText("");
                holder.messageSenderTextView.setBackgroundResource(0);
                holder.messageReceiverTextView.setBackgroundResource(R.drawable.text_message_background);
                holder.messageReceiverTextView.setText(messageChat.getText());
            }
        }
    }

    @Override
    public int getItemCount() {
        return messageChatList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView messageSenderTextView;
        TextView messageReceiverTextView;
        LinearLayout leftSide;
        LinearLayout rightSide;

        public MyViewHolder(View view) {
            super(view);
            leftSide = (LinearLayout) view.findViewById(R.id.left_side);
            rightSide = (LinearLayout) view.findViewById(R.id.right_side);
            messageSenderTextView = (TextView) view.findViewById(R.id.sender_text_message);
            messageReceiverTextView = (TextView) view.findViewById(R.id.receiver_text_message);
        }
    }
}
