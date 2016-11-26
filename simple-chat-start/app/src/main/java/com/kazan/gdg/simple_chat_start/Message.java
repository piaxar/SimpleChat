package com.kazan.gdg.simple_chat_start;

/**
 * @author ravil
 */

public class Message {

    private String mName;

    private String mText;

    public Message() {
    }

    public Message(String name, String text) {
        mName = name;
        mText = text;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }
}
