package com.example.velkhana_store.Model;

public class Payment {
    private String gameId;
    private String accid;
    private String email;
    private String phone;
    private String jumlah;
    private String platform;    private String imageURL;
    private String status;

    public Payment() {
        // Default constructor required for calls to DataSnapshot.getValue(Payment.class)
    }

    public Payment(String gameId, String accid, String email, String phone, String jumlah, String platform, String imageURL, String status) {
        this.gameId = gameId;
        this.accid = accid;
        this.email = email;
        this.phone = phone;
        this.jumlah = jumlah;
        this.platform = platform;
        this.imageURL = imageURL;
        this.status = status;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
