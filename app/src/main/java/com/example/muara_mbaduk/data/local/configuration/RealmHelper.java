package com.example.muara_mbaduk.data.local.configuration;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.muara_mbaduk.data.local.model.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {

    Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    public void saveUser(UserModel userModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                UserModel model = realm.copyToRealm(userModel);
            }
        });
    }


    public void delete(){
      realm
              .executeTransaction(new Realm.Transaction() {
                  @Override
                  public void execute(Realm realm) {
                      realm.deleteAll();
                  }
              });
    }


    public UserModel findByJwt(String jwt) {
        UserModel userModel = realm.where(UserModel.class).equalTo("jwt", jwt).findFirst();
        if (userModel != null) {
            Log.i("jwt", "findByJwt: " + userModel.getJwt());
        } else {
            Log.e("jwt", "findByJwt: ");
        }
        return userModel;
    }


    public List<UserModel> findAllUser() {
        RealmResults<UserModel> results = realm.where(UserModel.class).findAll();
        return results;
    }
}
