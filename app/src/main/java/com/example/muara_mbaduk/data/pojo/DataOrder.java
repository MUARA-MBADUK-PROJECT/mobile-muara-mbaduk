package com.example.muara_mbaduk.data.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DataOrder implements Serializable {
    private List<Map<String, String>> dataList;

    public DataOrder(List<Map<String, String>> dataList) {
        this.dataList = dataList;
    }

    public List<Map<String, String>> getDataList() {
        return dataList;
    }
}
