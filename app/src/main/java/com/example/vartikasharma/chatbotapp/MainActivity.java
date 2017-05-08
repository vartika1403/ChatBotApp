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
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    @BindView(R.id.message_edit_text) /* package-local */ EditText messageEditText;
    @BindView(R.id.messenger_send_button) /* package-local */ Button sendButton;
    @BindView(R.id.message_recycler_view) /* package-local */ RecyclerView messageRecyclerView;
    private List<MessageChat> messageChatList;
    private ChatAdapter chatAdapter;
    private DatabaseReference firebaseChatRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        String chatId = "chirag1".concat("63906");
        String userConversationUri = Conf.firebaseConverstionUri(chatId);
        if (userConversationUri == null || userConversationUri.isEmpty()) {
            Log.i(LOG_TAG, "Empty userConversationUri");
            Toast.makeText(MainActivity.this, R.string.toast_error_message_on_displaying_conversation,
                    Toast.LENGTH_LONG).show();
            return;
        } else {
            Log.i(LOG_TAG, "firebase userConversationUri, " + userConversationUri);
        }
        firebaseChatRef = FirebaseDatabase.getInstance().getReferenceFromUrl(userConversationUri);
        if (firebaseChatRef == null) {
            return;
        }

        messageChatList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        firebaseChatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot itemDataSnapshot : dataSnapshot.getChildren()) {
                    MessageChat messageChat = itemDataSnapshot.getValue(MessageChat.class);
                    messageChatList.add(messageChat);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        MessageChat messageChat = new MessageChat(messageEditText.getText().toString(), true);
        messageChatList.add(messageChat);
        firebaseChatRef.push().setValue(messageChat);
        chatAdapter.notifyDataSetChanged();
        sendMessageToServer();
        messageEditText.setText("");
    }

    private void sendMessageToServer() {
        String message = messageEditText.getText().toString();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("www.personalityforge.com")
                .addPathSegment("api")
                .addPathSegment("chat")
                .addQueryParameter("apiKey", "6nt5d1nJHkqbkphe")
                .addQueryParameter("message", message)
                .addQueryParameter("chatBotID", "63906")
                .addQueryParameter("externalID", "chirag1")
                .build();
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
                        try {
                            JSONObject jsonObj = new JSONObject(myResponse);
                            JSONObject object = jsonObj.getJSONObject("message");
                            String receivedMessage = object.getString("message");
                            MessageChat messageChat = new MessageChat(receivedMessage, false);
                            messageChatList.add(messageChat);
                            chatAdapter.notifyDataSetChanged();

                            firebaseChatRef.push().setValue(messageChat);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
}

