package com.example.vartikasharma.chatbotapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String MESSAGES_CHILD = "chat-message";
    @BindView(R.id.message_edit_text) /* package-local */ EditText messageEditText;
    @BindView(R.id.messenger_send_button) /* package-local */ Button sendButton;
    @BindView(R.id.message_recycler_view) /* package-local */ RecyclerView messageRecyclerView;
    private String profilePicUrl;
    private List<MessageChat> messageChatList;
    private LinearLayoutManager linearLayoutManager;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        messageChatList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageEditText.addTextChangedListener(new TextWatcher() {
                                                   @Override
                                                   public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                   }

                                                   @Override
                                                   public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                       if (charSequence.toString().trim().length() > 0) {
                                                           sendButton.setEnabled(true);
                                                       } else {
                                                           sendButton.setEnabled(false);
                                                       }
                                                   }

                                                   @Override
                                                   public void afterTextChanged(Editable editable) {

                                                   }
                                               });
        chatAdapter = new ChatAdapter(messageChatList);
        messageRecyclerView.setLayoutManager(linearLayoutManager);
        messageRecyclerView.setAdapter(chatAdapter);
    }

    @OnClick(R.id.messenger_send_button)
    void sendMessage() {
        MessageChat messageChat = new MessageChat(messageEditText.getText().toString());
        messageChatList.add(messageChat);
        messageEditText.setText("");
        chatAdapter = new ChatAdapter(messageChatList);
        messageRecyclerView.setLayoutManager(linearLayoutManager);
        messageRecyclerView.setAdapter(chatAdapter);
        //send data to server http request

    }
}
