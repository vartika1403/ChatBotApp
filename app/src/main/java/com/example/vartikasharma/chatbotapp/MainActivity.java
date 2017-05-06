package com.example.vartikasharma.chatbotapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String MESSAGES_CHILD = "chat-message";
    @BindView(R.id.message_edit_text) /* package-local */ EditText messageEditText;
    @BindView(R.id.messenger_send_button) /* package-local */ Button sendButton;
    @BindView(R.id.message_recycler_view) /* package-local */ RecyclerView messageRecyclerView;
    private String profilePicUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        List<MessageChat> messageChatList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        ChatAdapter chatAdapter = new ChatAdapter(messageChatList);
        messageRecyclerView.setLayoutManager(linearLayoutManager);
        messageRecyclerView.setAdapter(chatAdapter);
    }
}
