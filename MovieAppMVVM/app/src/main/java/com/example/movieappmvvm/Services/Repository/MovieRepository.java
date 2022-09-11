package com.example.movieappmvvm.Services.Repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.movieappmvvm.Services.Model.Result;
import com.example.movieappmvvm.Services.Model.TopRatedMovieModel;
import com.example.movieappmvvm.Services.Network.ApiServices;
import com.example.movieappmvvm.Services.Network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static Context myContext;
    private static MovieRepository instance;
    private TopRatedMovieModel topRatedMovieModel;
    private List<Result> myResult;
    private MutableLiveData mLiveData;

    public static MovieRepository getInstance(Context context){
        if (instance == null){
             myContext = context;
            instance = new MovieRepository();
        }
        return instance;
    }


    public MutableLiveData<List<Result>> getTopRatedMovieList(){

        if (mLiveData == null){
            mLiveData = new MutableLiveData();
        }

        ApiServices apiServices = RetrofitInstance.getRetrofitInstance().create(ApiServices.class);
        Call<TopRatedMovieModel> call = apiServices.getTopRatedMovieList();
        call.enqueue(new Callback<TopRatedMovieModel>() {
            @Override
            public void onResponse(Call<TopRatedMovieModel> call, Response<TopRatedMovieModel> response) {
                topRatedMovieModel = response.body();
                myResult = topRatedMovieModel.getResults();
                mLiveData.postValue(myResult);
            }

            @Override
            public void onFailure(Call<TopRatedMovieModel> call, Throwable t) {

            }
        });
        return mLiveData;
    }
}
