package com.example.muara_mbaduk.model.response;

import androidx.annotation.NonNull;

import com.example.muara_mbaduk.model.ParrentModel;
import com.example.muara_mbaduk.model.entity.Review;

import java.util.Objects;

public class ReviewStoreResponse extends ParrentModel {

    private Review data;

    public Review getData() {
        return data;
    }

    public void setData(Review data) {
        this.data = data;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewStoreResponse that = (ReviewStoreResponse) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
