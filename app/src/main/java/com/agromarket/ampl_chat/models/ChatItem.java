package com.agromarket.ampl_chat.models;

public class ChatItem {
    private int customerId;
    private String email;

    private String name;
    private String message;
    private String time;
    private int unreadCount;

    public ChatItem(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;

        // Default values for now (until you implement real messages)
        this.message = "Tap to chat";
        this.time = "";
        this.unreadCount = 0;
    }

    public int getCustomerId() { return customerId; }
    public String getEmail() { return email; }

    public String getName() { return name; }
    public String getMessage() { return message; }
    public String getTime() { return time; }
    public int getUnreadCount() { return unreadCount; }
}