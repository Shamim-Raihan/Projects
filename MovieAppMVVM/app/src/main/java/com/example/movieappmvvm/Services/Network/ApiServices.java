package com.example.movieappmvvm.Services.Network;

import com.example.movieappmvvm.Services.Model.TopRatedMovieModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {
    @GET("3/movie/top_rated?api_key=42d31aa15cf10c625b159d4a7e30548f")
    Call<TopRatedMovieModel> getTopRatedMovieList();
}
