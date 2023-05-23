package com.example.muara_mbaduk.model.response;

import com.example.muara_mbaduk.model.ParrentModel;
import com.example.muara_mbaduk.model.entity.Review;

import java.io.Serializable;
import java.util.List;

public class ReviewResponse extends ParrentModel implements Serializable {

    private List<Review> data;

    public List<Review> getData() {
        return data;
    }

    public void setData(List<Review> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "ReviewResponse{" +
                "data=" + data +
                '}';
    }
}
