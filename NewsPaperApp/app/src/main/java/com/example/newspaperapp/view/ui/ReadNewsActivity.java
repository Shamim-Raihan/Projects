package com.example.newspaperapp.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newspaperapp.R;

public class ReadNewsActivity extends AppCompatActivity {

    private String newsTitle, newsCategory, newsExcerpt, newsImage, newsDate;
    private TextView newsTitleTv, newsCategoryTv, newsExcerptTv, newsDateTv;
    private ImageView newsImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_news);

        newsTitleTv = findViewById(R.id.title_id);
        newsCategoryTv = findViewById(R.id.category_id);
        newsExcerptTv = findViewById(R.id.details_id);
        newsDateTv = findViewById(R.id.date_id);
        newsImageView = findViewById(R.id.imageView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            newsTitle = bundle.getString("title");
            newsCategory = bundle.getString("category");
            newsExcerpt = bundle.getString("excerpt");
            newsImage = bundle.getString("image");
            newsDate = bundle.getString("date");
        }

        newsTitleTv.setText(newsTitle);
        newsCategoryTv.setText(newsCategory);
        newsExcerptTv.setText(newsExcerpt);
        newsDateTv.setText(newsDate);
        Glide.with(ReadNewsActivity.this).load(newsImage).into(newsImageView);
    }
}