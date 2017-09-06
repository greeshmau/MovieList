package com.example.gumapathi.movielist.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gumapathi.movielist.R;

/**
 * Created by gumapathi on 9/6/2017.
 */

public class PopularMovieHolder extends RecyclerView.ViewHolder {
public TextView tvTitle;
public TextView tvOverview;
public ImageView ivMovieImage;

public PopularMovieHolder(View itemView) {
    super(itemView);

    tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
    tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
    ivMovieImage = (ImageView) itemView.findViewById(R.id.ivMovieImage);
    }
}
