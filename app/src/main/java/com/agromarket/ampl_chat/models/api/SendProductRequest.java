package com.agromarket.ampl_chat.models.api;

public class SendProductRequest {
    public int receiver_id;
    public int product_id;

    public SendProductRequest(int receiver_id, int product_id) {
        this.receiver_id = receiver_id;
        this.product_id = product_id;
    }
}
