package com.example.vartikasharma.chatbotapp;


import android.util.Log;

import java.util.UUID;

public class Conf {
    public static final String FIREBASE_CHAT_URI = "chat/"; // chat URI
    private static final String FIREBASE_DOMAIN_URI = "https://androidchatproject-9a048.firebaseio.com/";
    public static final String FIREBASE_CONVERSATION_URI = "conversation/"; //conversation URI

    public static String firebaseDomainUri() {
        return FIREBASE_DOMAIN_URI;
    }

    public static String firebaseConverstionUri(String chatId) {
        if (chatId != null && !chatId.isEmpty()) {
            return firebaseDomainUri() + FIREBASE_CHAT_URI + FIREBASE_CONVERSATION_URI + chatId + "/" + "message-list/";
        }
        return "";
    }

    public static String getChatId(String userId1, String userId2) {
        if ((userId1 != null &&!userId1.isEmpty()) && (userId2 != null && userId2.isEmpty())) {
            Log.i("chat id", userId1);
            Log.i("chat id2", userId2);
            int compare = userId1.compareToIgnoreCase(userId2);
            String concatString = "";
            if (compare < 0) {
                concatString = userId1.concat(userId2);
            } else {
                concatString = userId2.concat(userId1);
            }
            byte[] concatStringByte = concatString.getBytes();
            return UUID.nameUUIDFromBytes(concatStringByte).toString();
        }
        return "";
    }
}
