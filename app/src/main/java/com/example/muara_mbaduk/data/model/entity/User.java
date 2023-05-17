package com.example.muara_mbaduk.data.model.entity;

import com.example.muara_mbaduk.data.model.ParrentModel;

public class User {

    private String id;
    private String fullname;
    private String images;
    private String email;
    private long iat;
    private long exp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getIat() {
        return iat;
    }

    public void setIat(int iat) {
        this.iat = iat;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
