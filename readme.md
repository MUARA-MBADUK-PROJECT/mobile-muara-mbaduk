
# APLIKASI PEMESANAN TIKET WISATA MUARA MBADUK

Aplikasi ini dibuat menggunakan beberapa teknologi antara lain
- java 17
- graddle
- mysql
- realm database lokal
- junit
- Android




## Bagaimana Menggunakan realm

### buat file konfigurasi default untuk realm database

```java
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

```

### ubah nama application pada android manifest
``` java

android:name=".data.local.configuration.MuaraMbadukConfiguration"

```

### buat realm helper agar lebih mudah dalam mengoprasikan operasi database lokal

``` java

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

    public  RealmHelper(Realm realm){
        this.realm = realm;
    }

    public void saveUser(UserModel userModel){
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(@NonNull Realm realm) {
                        UserModel model = realm.copyToRealm(userModel);
                    }
                });
            }
            
            public UserModel findByJwt(String jwt){
                UserModel userModel = realm.where(UserModel.class).equalTo("jwt" , jwt).findFirst();
                if(userModel!=null){
                    Log.i("jwt", "findByJwt: " + userModel.getJwt());
                }else{
                    Log.e("jwt", "findByJwt: ");
                }
                return userModel;
            }


    public List<UserModel> findAllUser(){
        RealmResults<UserModel> results = realm.where(UserModel.class).findAll();
        return results;
    }
}

```


## Authors

- [@Mohammad Tajut ZamZami](https://www.github.com/tajutzam)

