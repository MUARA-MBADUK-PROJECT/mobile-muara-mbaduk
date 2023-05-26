package com.example.muara_mbaduk.model.response;

import com.example.muara_mbaduk.model.ParrentModel;

public class ReviewUpdateResponse extends ParrentModel {

    private ReviewDataResponse data;


    public ReviewDataResponse getData() {
        return data;
    }

    public void setData(ReviewDataResponse data) {
        this.data = data;
    }

    static class ReviewDataResponse{
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
    }
}
