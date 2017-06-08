package com.example.rxbro.retrofitapp;

import com.example.rxbro.retrofitapp.RandomUser.RandomUser;
import com.example.rxbro.retrofitapp.RandomUser.User;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by rxbro on 6/6/2017.
 */

public interface RetrofitObject {
    @GET("api/RetrofitAndroidObjectResponse")
    Call<RandomUser> getRandomUser();

}
