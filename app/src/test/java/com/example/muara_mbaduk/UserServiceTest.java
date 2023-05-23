package com.example.muara_mbaduk;

import com.example.muara_mbaduk.data.local.UserService;
import com.example.muara_mbaduk.data.local.configuration.RealmHelper;
import com.example.muara_mbaduk.data.local.model.UserModel;
import com.example.muara_mbaduk.model.entity.User;

import org.junit.Test;

import io.realm.Realm;

public class UserServiceTest {


    @Test
    public void findUserByjwtTest(){
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImRkMDA2NGM4LTRmM2MtNGI2ZS05ZWIzLWMwMjg0Y2IzOGY3OCIsImZ1bGxuYW1lIjoiTW9oYW1tYWQgdGFqdXQgWmFtIHphbWkiLCJlbWFpbCI6Im1vaGFtbWFkdGFqdXR6YW16YW1pMDdAZ21haWwuY29tIiwiaW1hZ2VzIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUdObXl4WmFDNENQNTJJUVR3VFp1OTY5ZGVreVdWNnAtMlF5eGRZdFJYRGw9czk2LWMiLCJpYXQiOjE2ODQzNDkxMzUsImV4cCI6MTY4NDk1MzkzNX0.2c5SasJUjraVXkdLjWAD2U4N3jM-Wv3ruOEqLxJhwLc";
        UserService userService = new UserService(new RealmHelper(Realm.getDefaultInstance()));
        UserModel userModel = userService.findOne(jwt);
        System.out.println(userModel.toString());
    }
}
