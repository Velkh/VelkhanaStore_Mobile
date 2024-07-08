package com.example.velkhana_store.Model;

public class Comments {
    private String username;
    private String comms;

    public Comments() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Comments(String username, String comms) {
        this.username = username;
        this.comms = comms;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComms() {
        return comms;
    }

    public void setComms(String comms) {
        this.comms = comms;
    }
}
