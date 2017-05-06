package com.example.vartikasharma.chatbotapp;


public class MessageChat {
    private String id;
    private String text;
    private String name;

    public MessageChat() {

    }
    public MessageChat(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }
}
