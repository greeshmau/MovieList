package com.example.gumapathi.movielist.Adapter;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.gumapathi.movielist.Activities.YoutubePlayerActivity;
import com.example.gumapathi.movielist.Model.Movie;
import com.example.gumapathi.movielist.R;
import com.example.gumapathi.movielist.Views.PopularMovieHolder;
import com.example.gumapathi.movielist.Views.UnpopularMovieHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by gumapathi on 8/30/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final int POPULAR = 1;
    final int UNPOPULAR = 0;
    private List<Movie> moviesList;

    public MoviesAdapter(List<Movie> moviesListList) {
        this.moviesList = moviesListList;
    }

    @Override
    public int getItemViewType(int position) {
        Movie mi = moviesList.get(position);
        if (mi.vote_average > 5) {
            return POPULAR;
        }
        return UNPOPULAR;
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder movieViewHolder, int position) {
        final Movie mi = moviesList.get(position);
        int orientation = movieViewHolder.itemView.getContext().getResources().getConfiguration().orientation;

        if (movieViewHolder.getItemViewType() == POPULAR) {
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                final PopularMovieHolder holder = (PopularMovieHolder) movieViewHolder;
                holder.tvTitle.setText(mi.title);
                holder.tvOverview.setText(mi.overview);
                Log.i("SAMY-adp", mi.backdrop_path);
                String imageUri = mi.backdrop_path;
                ImageView ivBasicImage = holder.ivMovieImage;
                Picasso.with(holder.ivMovieImage.getContext())
                        .load(imageUri)
                        .placeholder(R.drawable.placeholder)
                        .transform(new RoundedCornersTransformation(15, 15, RoundedCornersTransformation.CornerType.ALL))
                        .into(ivBasicImage);

                holder.ivPlayIcon.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.ivPlayIcon.getContext(), YoutubePlayerActivity.class);
                        Log.i("SAMY-onClick", toString().valueOf(mi.movie_id));
                        intent.putExtra("movieid", toString().valueOf(mi.movie_id));
                        holder.ivPlayIcon.getContext().startActivity(intent);
                    }
                });
            }
            else {
                final PopularMovieHolder holder = (PopularMovieHolder) movieViewHolder;
                holder.tvTitle.setText(mi.title);
                Log.i("SAMY-adp", mi.backdrop_path);
                String imageUri = mi.backdrop_path;
                ImageView ivBasicImage = holder.ivMovieImage;
                Picasso.with(holder.ivMovieImage.getContext())
                        .load(imageUri)
                        .placeholder(R.drawable.placeholder)
                        .transform(new RoundedCornersTransformation(15, 15, RoundedCornersTransformation.CornerType.ALL))
                        .into(ivBasicImage);
                holder.ivPlayIcon.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.ivPlayIcon.getContext(), YoutubePlayerActivity.class);
                        Log.i("SAMY-onClick", toString().valueOf(mi.movie_id));
                        intent.putExtra("movieid", toString().valueOf(mi.movie_id));
                        holder.ivPlayIcon.getContext().startActivity(intent);
                    }
                });
            }
        }
        else {
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                UnpopularMovieHolder holder = (UnpopularMovieHolder) movieViewHolder;
                holder.tvTitle.setText(mi.title);
                holder.tvOverview.setText(mi.overview);
                String imageUri = mi.backdrop_path;
                ImageView ivBasicImage = holder.ivMovieImage;
                Picasso.with(holder.ivMovieImage.getContext())
                        .load(imageUri)
                        .placeholder(R.drawable.placeholder)
                        .transform(new RoundedCornersTransformation(15, 15, RoundedCornersTransformation.CornerType.ALL))
                        .into(ivBasicImage);
            }
            else {
                UnpopularMovieHolder holder = (UnpopularMovieHolder) movieViewHolder;
                holder.tvTitle.setText(mi.title);
                holder.tvOverview.setText(mi.overview);
                Log.i("SAMY-adp-else", mi.poster_path);
                String imageUri = mi.poster_path;
                ImageView ivBasicImage = holder.ivMovieImage;
                Picasso.with(holder.ivMovieImage.getContext())
                        .load(imageUri)
                        .placeholder(R.drawable.placeholder)
                        .transform(new RoundedCornersTransformation(15, 15, RoundedCornersTransformation.CornerType.ALL))
                        .into(ivBasicImage);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = null;
        if (viewType == POPULAR) {
            itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.movie_card_popular, viewGroup, false);
            //Picasso.with(viewGroup.getContext()).load(movie.getBackdropPath()).placeholder(R.drawable.placeholder).transform(new RoundedCornersTransformation(15, 15, RoundedCornersTransformation.CornerType.ALL)).into(ivMovieImage);
            return new PopularMovieHolder(itemView);
        } else {
            itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.movie_card_unpopular, viewGroup, false);
            return new UnpopularMovieHolder(itemView);
        }
    }
}
