package com.example.movieappmvvm.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.movieappmvvm.Services.Model.Result;
import com.example.movieappmvvm.Services.Repository.MovieRepository;
import java.util.List;

public class MovieListViewModel extends AndroidViewModel {

    private MovieRepository mRepo;

    public MovieListViewModel(@NonNull Application application) {
        super(application);
        mRepo = MovieRepository.getInstance(application);
    }

    public LiveData<List<Result>> getTopRatedMovieListFinal(){
        return mRepo.getTopRatedMovieList();
    }
}
