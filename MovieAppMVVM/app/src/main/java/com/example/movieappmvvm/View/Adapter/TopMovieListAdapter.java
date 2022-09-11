package com.example.movieappmvvm.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieappmvvm.R;
import com.example.movieappmvvm.Services.Model.Result;

import java.util.List;

public class TopMovieListAdapter extends RecyclerView.Adapter<TopMovieListAdapter.MyViewHolder>{

    private Context context;
    private List<Result> mResult;

    public TopMovieListAdapter(Context context, List<Result> mResult) {
        this.context = context;
        this.mResult = mResult;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_movie_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title.setText(mResult.get(position).getTitle());
        holder.rating.setText(mResult.get(position).getVoteAverage().toString());
        holder.releaseDate.setText(mResult.get(position).getReleaseDate());

        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + mResult.get(position).getPosterPath()).into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        if (this.mResult != null){
            return mResult.size();

        }
        else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView moviePoster;
        TextView title;
        TextView rating;
        TextView releaseDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.ImageViewId);
            title = itemView.findViewById(R.id.titleId);
            rating= itemView.findViewById(R.id.ratingId);
            releaseDate= itemView.findViewById(R.id.releaseDateId);
        }
    }



}







































