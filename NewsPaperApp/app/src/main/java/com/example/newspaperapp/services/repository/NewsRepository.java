package com.example.newspaperapp.services.repository;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.example.newspaperapp.services.model.ArticlesObjectClass;
import com.example.newspaperapp.services.model.MainObjectClass;
import com.example.newspaperapp.services.network.ApiServices;
import com.example.newspaperapp.services.network.RetrofitInstance;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private MutableLiveData mutableLiveData;
    private List<ArticlesObjectClass> newsList;
    private MainObjectClass mainObjectClass;
    private static NewsRepository instance;
    private static Context mContext;

    public static NewsRepository getInstance(Context context){
        if (instance == null){
            mContext = context;
            instance = new NewsRepository();
        }
        return instance;
    }

    public MutableLiveData<List<ArticlesObjectClass>> getAllNewsList(){
        if (mutableLiveData == null){
            mutableLiveData = new MutableLiveData();
        }

        ApiServices apiServices = RetrofitInstance.getRetrofitInstance().create(ApiServices.class);
        Call<MainObjectClass> call = apiServices.getAllNews();
        call.enqueue(new Callback<MainObjectClass>() {
            @Override
            public void onResponse(Call<MainObjectClass> call, Response<MainObjectClass> response) {
                mainObjectClass = response.body();
                newsList = mainObjectClass.getArticles();
                mutableLiveData.postValue(newsList);
            }

            @Override
            public void onFailure(Call<MainObjectClass> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
}
