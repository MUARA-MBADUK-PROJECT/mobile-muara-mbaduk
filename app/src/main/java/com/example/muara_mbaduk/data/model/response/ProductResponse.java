package com.example.muara_mbaduk.data.model.response;

import com.example.muara_mbaduk.data.model.ParrentModel;
import com.example.muara_mbaduk.data.model.entity.Products;
import com.example.muara_mbaduk.data.model.entity.ProductsModel;

import java.util.List;

public class ProductResponse extends ParrentModel {

    private List<ProductsModel> data;

    public List<ProductsModel> getData() {
        return data;
    }

    public void setData(List<ProductsModel> data) {
        this.data = data;
    }
}
