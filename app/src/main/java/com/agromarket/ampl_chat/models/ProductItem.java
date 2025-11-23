package com.agromarket.ampl_chat.models;

public class ProductItem {
    public int id;
    public String name;
    public String imageUrl; // full URL ready for Glide
    public String price;

    public ProductItem(int id, String name, String imageUrl, String price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }
}
