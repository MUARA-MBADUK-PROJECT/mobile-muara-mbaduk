package com.example.muara_mbaduk.data.model.response;

import com.example.muara_mbaduk.data.model.ParrentModel;
import com.example.muara_mbaduk.data.model.entity.User;

public class UserValidateResponse extends ParrentModel {
    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
