package com.agromarket.ampl_chat.models;

public class MessageItem {

    public static final int TYPE_TEXT = 0;
    public static final int TYPE_IMAGE = 1;

    public static final int STATUS_SENDING = 0;
    public static final int STATUS_SENT = 1;
    public static final int STATUS_FAILED = 2;

    public int type;
    public String text;

    public int productId;
    public String imageUrl;
    public boolean isSent;

    // NEW
    public int status = STATUS_SENDING;
    public String localId; // UUID to identify retry message
}
