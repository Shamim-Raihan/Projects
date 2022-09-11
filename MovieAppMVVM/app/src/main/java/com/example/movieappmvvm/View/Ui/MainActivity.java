package com.example.movieappmvvm.View.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.movieappmvvm.R;
import com.example.movieappmvvm.Services.Model.Result;
import com.example.movieappmvvm.Services.Repository.MovieRepository;
import com.example.movieappmvvm.View.Adapter.TopMovieListAdapter;
import com.example.movieappmvvm.ViewModel.MovieListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieListViewModel mViewModel;
    private TopMovieListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView = findViewById(R.id.recyclerViewId);
        mViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        mViewModel.getTopRatedMovieListFinal().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {

                adapter = new TopMovieListAdapter(MainActivity.this,results);
                recyclerView.setAdapter(adapter);

            }
        });
    }
}

















