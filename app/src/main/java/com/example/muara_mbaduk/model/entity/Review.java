package com.example.muara_mbaduk.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Review implements Serializable {

    private String id;

    private String fullname;
    private String payment;
    private String images;
    private String star;
    private String description;
    private String created_at;
    private String updated_at;

    @JsonProperty("package")
    private String packages;


    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }


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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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


    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", fullname='" + fullname + '\'' +
                ", payment='" + payment + '\'' +
                ", images='" + images + '\'' +
                ", star='" + star + '\'' +
                ", description='" + description + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", packages='" + packages + '\'' +
                '}';
    }
}
