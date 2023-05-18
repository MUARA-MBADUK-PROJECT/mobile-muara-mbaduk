package com.example.muara_mbaduk.data.model.entity;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class Tiket {
    private String id;
    private String title;
    private String category;
    private int normal_day;
    private int weekend_day;
    private String created_at;
    private String updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNormal_day() {
        return normal_day;
    }

    public void setNormal_day(int normal_day) {
        this.normal_day = normal_day;
    }

    public int getWeekend_day() {
        return weekend_day;
    }

    public void setWeekend_day(int weekend_day) {
        this.weekend_day = weekend_day;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}