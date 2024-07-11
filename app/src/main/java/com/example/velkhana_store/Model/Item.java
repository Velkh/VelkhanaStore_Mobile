package com.example.velkhana_store.Model;

public class Item {
    private String category;
    private String description;
    private String imageUrl;
    private String name;
    public String id;

    public Item() {
        // Default constructor required for calls to DataSnapshot.getValue(Item.class)
    }

    public Item(String category, String description, String imageUrl, String name) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.imageUrl = imageUrl;
        this.name = name;
    }

    // Getter for ID
    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
