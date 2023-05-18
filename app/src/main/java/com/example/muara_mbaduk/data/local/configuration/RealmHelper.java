package com.example.muara_mbaduk.data.local.configuration;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.muara_mbaduk.data.local.model.UserModel;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {

    Realm realm;

    public  RealmHelper(Realm realm){
        this.realm = realm;
    }

    public void saveUser(UserModel userModel){
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(@NonNull Realm realm) {
                        System.out.println("oke");
                        Log.i("realm", "Database was created");
                        String uuid = UUID.randomUUID().toString();
                        userModel.setId(uuid);
                        UserModel model = realm.copyToRealm(userModel);
                    }
                });
            }



    public List<UserModel> findAllUser(){
        RealmResults<UserModel> results = realm.where(UserModel.class).findAll();
        return results;
    }
}
