package com.example.newspaperapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.newspaperapp.services.model.ArticlesObjectClass;
import com.example.newspaperapp.services.repository.NewsRepository;

import java.util.List;

public class NewsListViewModel extends AndroidViewModel {

    private NewsRepository newsRepository;

    public NewsListViewModel(@NonNull Application application) {
        super(application);
        newsRepository = NewsRepository.getInstance(application);
    }

    public LiveData<List<ArticlesObjectClass>> getAllNewsList(){
        return newsRepository.getAllNewsList();
    }
}
