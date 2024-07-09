package com.example.velkhana_store.Model;

public class Comments {
    private String name;
    private String comment;
    private String timestamp;

    public Comments() {
        // Default constructor required for calls to DataSnapshot.getValue(Comments.class)
    }

    public Comments(String name, String comment, String timestamp) {
        this.name = name;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
