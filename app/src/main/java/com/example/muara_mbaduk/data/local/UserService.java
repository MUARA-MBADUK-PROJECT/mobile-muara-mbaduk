package com.example.muara_mbaduk.data.local;

import com.example.muara_mbaduk.data.local.configuration.RealmHelper;
import com.example.muara_mbaduk.data.local.model.UserModel;

public class UserService {

    RealmHelper realmHelper;

    public UserService(RealmHelper realmHelper) {
        this.realmHelper = realmHelper;
    }

    public UserModel findOne(String jwt){
        return realmHelper.findByJwt(jwt);
    }
}
