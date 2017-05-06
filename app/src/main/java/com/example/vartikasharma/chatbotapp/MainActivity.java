package com.example.vartikasharma.chatbotapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String MESSAGES_CHILD = "chat-message";
    private static final String URL ="https://www.personalityforge.com/api/chat/?apiKey=6nt5d1nJHkqbkphe&message=Hi&chatBotID=63906&externalID=chirag1";
    @BindView(R.id.message_edit_text) /* package-local */ EditText messageEditText;
    @BindView(R.id.messenger_send_button) /* package-local */ Button sendButton;
    @BindView(R.id.message_recycler_view) /* package-local */ RecyclerView messageRecyclerView;
    private List<MessageChat> messageChatList;
    private LinearLayoutManager linearLayoutManager;
    private ChatAdapter chatAdapter;
    private boolean rightSide = true;

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
        /*chatAdapter = new ChatAdapter(messageChatList);
        messageRecyclerView.setLayoutManager(linearLayoutManager);
        messageRecyclerView.setAdapter(chatAdapter);*/
    }

    @OnClick(R.id.messenger_send_button)
    void sendMessage() {
        MessageChat messageChat = new MessageChat(messageEditText.getText().toString(), true);
        messageChatList.add(messageChat);
        chatAdapter = new ChatAdapter(messageChatList);
        messageRecyclerView.setLayoutManager(linearLayoutManager);
        messageRecyclerView.setAdapter(chatAdapter);
        sendMessageToServer();
        messageEditText.setText("");
        //send data to server http request
        //sendMessageToServer();
    }

    private void sendMessageToServer() {
        String message = messageEditText.getText().toString();
        Log.i(LOG_TAG, "message," + message);
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("www.personalityforge.com")
                .addPathSegment("api")
                .addPathSegment("chat")
                .addQueryParameter("apiKey", "6nt5d1nJHkqbkphe")
                .addQueryParameter("message", message)
                .addQueryParameter("chatBotID","63906")
                .addQueryParameter("externalID","chirag1")
                .build();
        Log.i(LOG_TAG, "url," + url);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final String myResponse = response.body().string();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // txtString.setText(myResponse);
                        try {
                            JSONObject jsonObj = new JSONObject(myResponse);
                            JSONObject object = jsonObj.getJSONObject("message");
                            Log.i(LOG_TAG, "object," + object);
                            String receivedMessage = object.getString("message");
                            Log.i(LOG_TAG, "receivedMessage," + receivedMessage);

                            MessageChat messageChat = new MessageChat(receivedMessage, false);
                            messageChatList.add(messageChat);
                            chatAdapter = new ChatAdapter(messageChatList);
                            messageRecyclerView.setLayoutManager(linearLayoutManager);
                            messageRecyclerView.setAdapter(chatAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.i(LOG_TAG, "response," + myResponse);

                    }
                });

            }
        });
    }
    }

