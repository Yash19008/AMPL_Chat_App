package com.agromarket.ampl_chat.models;

public class MessageItem {
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_IMAGE = 1;

    public int type;
    public String text;
    public String imageUrl;    // for product images
    public boolean isSent;     // true when sender == current user
    public String time;        // optional created_at

    // default empty constructor
    public MessageItem() {}
}
