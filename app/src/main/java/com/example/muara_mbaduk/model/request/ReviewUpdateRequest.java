package com.example.muara_mbaduk.model.request;

public class ReviewUpdateRequest {

    private int star;
    private String description;

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ReviewUpdateRequest{" +
                "star=" + star +
                ", description='" + description + '\'' +
                '}';
    }
}
