package com.example.vartikasharma.chatbotapp;


public class MessageChat {
    private String id;
    private String text;
    private String name;
    private boolean rightSide;

    public MessageChat() {

    }
    public MessageChat(String text, boolean rightSide) {
        this.text = text;
        this.rightSide = rightSide;
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
    public boolean isRightSide() {
        return rightSide;
    }

    public void setRightSide(boolean rightSide) {
        this.rightSide = rightSide;
    }
}
