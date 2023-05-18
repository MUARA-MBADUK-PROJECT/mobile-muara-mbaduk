package com.example.muara_mbaduk.data.dto;

import com.example.muara_mbaduk.model.entity.PackagePurchaseRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PackageOrder implements Serializable {

    private List<PackagePurchaseRequest> packagePurchaseRequests = new ArrayList<>();


    public List<PackagePurchaseRequest> getPackagePurchaseRequests() {
        return packagePurchaseRequests;
    }

    public void setPackagePurchaseRequests(List<PackagePurchaseRequest> packagePurchaseRequests) {
        this.packagePurchaseRequests = packagePurchaseRequests;
    }
}
