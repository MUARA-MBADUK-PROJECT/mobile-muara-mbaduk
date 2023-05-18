package com.example.muara_mbaduk.data.local.configuration;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MuaraMbadukConfiguration extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().
                name("user.db")
                        .allowQueriesOnUiThread(true)
                                .allowWritesOnUiThread(true)
                                        .
                build();
        Realm.setDefaultConfiguration(configuration);
    }
}
