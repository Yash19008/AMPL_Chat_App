package com.agromarket.ampl_chat.models.api;

public class VendorRegisterResponse {

    private boolean status;
    private String message;
    private String token;
    private User user;
    private Vendor vendor;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public String getToken() { return token; }
    public User getUser() { return user; }
    public Vendor getVendor() { return vendor; }

    private class User {
        public int id;
        public String name;
        public String email;
        public String role;
    }

    private class Vendor {
        public int user_id;
        public String firm_name;
        public String gst_number;
        public String license_type;
        public String address;
        public String phone_number;
    }
}