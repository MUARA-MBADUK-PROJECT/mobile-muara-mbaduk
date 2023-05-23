package com.example.muara_mbaduk.model.entity;


import com.example.muara_mbaduk.model.response.PackagesResponse;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Packages {

    private String id;
    private String title;
    private String slug;
    private String summary;
    private String category;
    private String description;
    private int price;
    private String image;
    ArrayList<Products> products;
    private String created_at;
    private String updated_at;
    private String updated_a;

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Products> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Products> products) {
        this.products = products;
    }

    public String getUpdated_a() {
        return updated_a;
    }

    public void setUpdated_a(String updated_a) {
        this.updated_a = updated_a;
    }


}
