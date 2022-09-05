package com.example.newspaperapp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newspaperapp.R;
import com.example.newspaperapp.services.model.ArticlesObjectClass;
import com.example.newspaperapp.view.ui.ReadNewsActivity;
import com.facebook.shimmer.Shimmer;

import java.util.List;

public class AllNewsAdapter extends RecyclerView.Adapter<AllNewsAdapter.MyViewHolder> {


    private Context context;
    private List<ArticlesObjectClass> newsList;
    int pos;
    String detail;

    public AllNewsAdapter(Context context, List<ArticlesObjectClass> newsList) {
        this.context = context;
        this.newsList = newsList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.news_item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Shimmer shimmer = new Shimmer.ColorHighlightBuilder()
                .setBaseColor(Color.parseColor("#F3F3F3"))
                .setBaseAlpha(1)
                .setHighlightColor(Color.parseColor("#E7E7E7"))
                .setHighlightAlpha(1)
                .setDropoff(50)
                .build();

        holder.newsTitle.setText(newsList.get(position).getTitle());
        holder.newsCategory.setText(newsList.get(position).getCategory());
        Glide.with(context).load(newsList.get(position).getImageUrl()).into(holder.newsImage);

        detail = newsList.get(position).getExcerpt();

        if (detail.equals("") || detail.equals("null")){
            detail = newsList.get(position).getContent();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReadNewsActivity.class);
                intent.putExtra("title", newsList.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("category", newsList.get(holder.getAdapterPosition()).getCategory());
                intent.putExtra("excerpt", detail);
                intent.putExtra("image", newsList.get(holder.getAdapterPosition()).getImageUrl());
                intent.putExtra("date", newsList.get(holder.getAdapterPosition()).getPublishedAt());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle;
        TextView newsCategory;
        ImageView newsImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.news_title_id);
            newsCategory = itemView.findViewById(R.id.new_category_id);
            newsImage = itemView.findViewById(R.id.image_view_id);
        }
    }

}
