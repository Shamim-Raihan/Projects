package com.example.newspaperapp.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newspaperapp.R;
import com.example.newspaperapp.services.model.ArticlesObjectClass;
import com.example.newspaperapp.view.adapter.AllNewsAdapter;
import com.example.newspaperapp.viewModel.NewsListViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsListViewModel viewModel;
    private AllNewsAdapter allNewsAdapter;
    private TextView allTv, bangladeshTv, economicsTv, internationalTv, scienceTv, playTv, healthTv;
    private ShimmerFrameLayout shimmerFrameLayout;

    private List<ArticlesObjectClass> bangladeshNewsList = new ArrayList<ArticlesObjectClass>();
    private List<ArticlesObjectClass> economicsNewsList = new ArrayList<ArticlesObjectClass>();
    private List<ArticlesObjectClass> internationalNewsList = new ArrayList<ArticlesObjectClass>();
    private List<ArticlesObjectClass> scienceNewsList = new ArrayList<ArticlesObjectClass>();
    private List<ArticlesObjectClass> playNewsList = new ArrayList<ArticlesObjectClass>();
    private List<ArticlesObjectClass> healthNewsList = new ArrayList<ArticlesObjectClass>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allTv = findViewById(R.id.all_id);
        bangladeshTv = findViewById(R.id.bangladesh_id);
        economicsTv = findViewById(R.id.economics_id);
        internationalTv = findViewById(R.id.international_id);
        scienceTv = findViewById(R.id.science_and_technology_id);
        playTv = findViewById(R.id.play_id);
        healthTv = findViewById(R.id.health_id);
        recyclerView = findViewById(R.id.recyclerView_id);
        shimmerFrameLayout = findViewById(R.id.shimmer_framelayout_id);

        viewModel = new ViewModelProvider(this).get(NewsListViewModel.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        viewModel.getAllNewsList().observe(this, new Observer<List<ArticlesObjectClass>>() {
            @Override
            public void onChanged(List<ArticlesObjectClass> articlesObjectClasses) {
                shimmerFrameLayout.hideShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                typingNews(articlesObjectClasses);

                allNewsAdapter = new AllNewsAdapter(MainActivity.this, articlesObjectClasses);
                recyclerView.setAdapter(allNewsAdapter);
            }
        });

        allTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bangladeshNewsList.clear();
                economicsNewsList.clear();
                internationalNewsList.clear();
                scienceNewsList.clear();
                playNewsList.clear();
                healthNewsList.clear();
                viewModel.getAllNewsList().observe(MainActivity.this, new Observer<List<ArticlesObjectClass>>() {
                    @Override
                    public void onChanged(List<ArticlesObjectClass> articlesObjectClasses) {
                        shimmerFrameLayout.hideShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        allNewsAdapter = new AllNewsAdapter(MainActivity.this, articlesObjectClasses);
                        recyclerView.setAdapter(allNewsAdapter);
                    }
                });
            }
        });

        bangladeshTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allNewsAdapter = new AllNewsAdapter(MainActivity.this, bangladeshNewsList);
                recyclerView.setAdapter(allNewsAdapter);
            }
        });

        economicsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allNewsAdapter = new AllNewsAdapter(MainActivity.this, economicsNewsList);
                recyclerView.setAdapter(allNewsAdapter);
            }
        });

        internationalTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allNewsAdapter = new AllNewsAdapter(MainActivity.this, internationalNewsList);
                recyclerView.setAdapter(allNewsAdapter);
            }
        });

        scienceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allNewsAdapter = new AllNewsAdapter(MainActivity.this, scienceNewsList);
                recyclerView.setAdapter(allNewsAdapter);
            }
        });

        playTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allNewsAdapter = new AllNewsAdapter(MainActivity.this, playNewsList);
                recyclerView.setAdapter(allNewsAdapter);
            }
        });

        healthTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allNewsAdapter = new AllNewsAdapter(MainActivity.this, healthNewsList);
                recyclerView.setAdapter(allNewsAdapter);
            }
        });
    }

    private void typingNews(List<ArticlesObjectClass> articlesObjectClasses) {
        for (int i = 0; i < articlesObjectClasses.size(); i++) {
            String category = articlesObjectClasses.get(i).getCategory();
            if (category.equals("বাংলাদেশ")) {
                bangladeshNewsList.add(articlesObjectClasses.get(i));
            }
            if (category.equals("অর্থনীতি")) {
                economicsNewsList.add(articlesObjectClasses.get(i));
            }
            if (category.equals("আন্তর্জাতিক")) {
                internationalNewsList.add(articlesObjectClasses.get(i));
            }
            if (category.equals("বিজ্ঞান ও প্রযুক্তি")) {
                scienceNewsList.add(articlesObjectClasses.get(i));
            }
            if (category.equals("খেলা")) {
                playNewsList.add(articlesObjectClasses.get(i));
            }
            if (category.equals("স্বাস্থ্য")) {
                healthNewsList.add(articlesObjectClasses.get(i));
            }
        }
    }
}