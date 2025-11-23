package com.agromarket.ampl_chat.models.api;

public class SendMessageRequest {
    public int receiver_id;
    public String message;

    public SendMessageRequest(int receiver_id, String message) {
        this.receiver_id = receiver_id;
        this.message = message;
    }
}
