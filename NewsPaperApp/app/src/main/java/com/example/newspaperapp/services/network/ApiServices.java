package com.example.newspaperapp.services.network;

import com.example.newspaperapp.services.model.MainObjectClass;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {

    @GET("recent?country=bd&apiKey=61e3240b4311b161e3240b4312e29418645132")
    Call<MainObjectClass> getAllNews();
}
