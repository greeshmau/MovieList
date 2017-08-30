package com.example.gumapathi.movielist.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gumapathi.movielist.Model.Movie;
import com.example.gumapathi.movielist.R;

import java.util.List;

/**
 * Created by gumapathi on 8/30/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>{
    private List<Movie> moviesList;

    public MoviesAdapter(List<Movie> contactList) {
        this.moviesList = contactList;
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int i) {
        Movie mi = moviesList.get(i);
        movieViewHolder.tvTitle.setText(mi.title);
        movieViewHolder.tvOverview.setText(mi.overview);
        //movieViewHolder.ivMovieImage.setImageURI(android.net. mi.poster_path);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.movie_card, viewGroup, false);

        return new MovieViewHolder(itemView);
    }
    public class MovieViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvTitle;
        public TextView tvOverview;
        public ImageView ivMovieImage;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public MovieViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            ivMovieImage = (ImageView) itemView.findViewById(R.id.ivMovieImage);
        }
    }
}
