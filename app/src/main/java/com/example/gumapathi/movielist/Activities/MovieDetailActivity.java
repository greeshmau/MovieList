package com.example.gumapathi.movielist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gumapathi.movielist.Model.Movie;
import com.example.gumapathi.movielist.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDetailActivity extends YouTubeBaseActivity {
    private static String API_KEY = "AIzaSyDZuLakIYICjMX_DlSOEiH6tM9uyLY0N2U";
    TextView tvTitle, tvOverview, tvPopularity;
    RatingBar ratingBar;
    ImageView ivMovieImgBgd;
    YouTubePlayerView ytpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        int movieId = intent.getIntExtra("movie_id", -1);
        Log.i("SAMY", "movie id: " + movieId);
        getMovieDetails(movieId);
        setYoutubePlayer(movieId);
    }

    private void getMovieDetails(int movieId) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed")
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String responseData = response.body().string();
                                JSONObject singleMoviejson = new JSONObject(responseData);

                                Movie mi = new Movie(singleMoviejson.getInt("id"),
                                        singleMoviejson.getString("original_title"),
                                        singleMoviejson.getString("overview"),
                                        "https://image.tmdb.org/t/p/w500" + singleMoviejson.getString("poster_path"),
                                        "https://image.tmdb.org/t/p/w500" + singleMoviejson.getString("backdrop_path"),
                                        singleMoviejson.getDouble("vote_average") / 2,
                                        singleMoviejson.getDouble("vote_average"),
                                        singleMoviejson.getDouble("popularity"));


                                tvTitle = (TextView) findViewById(R.id.tvTitle);
                                tvOverview = (TextView) findViewById(R.id.tvOverview);
                                tvPopularity = (TextView) findViewById(R.id.tvPopularity);
                                ratingBar = (RatingBar) findViewById(R.id.ratingBar);
                                ivMovieImgBgd = (ImageView) findViewById(R.id.ivMovieImgBgd);

                                Picasso.with(ivMovieImgBgd.getContext())
                                        .load(mi.poster_path)
                                        //.placeholder(R.drawable.placeholder)
                                        //.transform(new RoundedCornersTransformation(15, 15, RoundedCornersTransformation.CornerType.ALL))
                                        .into(ivMovieImgBgd);


                                tvTitle.setText(mi.title);
                                tvOverview.setText(mi.overview);
                                tvPopularity.setText(String.format("Popularity: %.2f", mi.popularity));

                                //Picasso.with(this).load(movie.getPosterPath()).placeholder(R.mipmap.ic_launcher).transform(new RoundedCornersTransformation(15, 15, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT)).into(ivMovieImage);
                                //ratingBar.setNumStars((int) Math.round(mi.vote_average) + 1);
                                //Log.i(TAG, "rating: " + mi.vote_average + " " + mi.vote_average.floatValue());
                                ratingBar.setStepSize((float) 0.01);

                                ratingBar.setRating((float) mi.stars);

                            } catch (Exception e) {

                            }
                        }
                    });
                }
            }
        });
    }

    private void setYoutubePlayer(final int movie_id) {
        ytpView = (YouTubePlayerView) findViewById(R.id.youtubePlayer);
        ytpView.initialize(API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        final YouTubePlayer youTubePlayer, boolean b) {

                        //get video trailer source
                        String url = "https://api.themoviedb.org/3/movie/" + movie_id + "/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
                        OkHttpClient client = new OkHttpClient();

                        Request request = new Request.Builder()
                                .url(url)
                                .build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, final Response response) throws IOException {
                                if (!response.isSuccessful()) {
                                    throw new IOException("Unexpected code " + response);
                                } else {
                                    //runOnUiThread(new Runnable() {
                                        //@Override
                                        //public void run() {
                                            JSONArray movieTrailerJsonResults = null;
                                            try {
                                                JSONObject respObj = new JSONObject(response.body().string());
                                                movieTrailerJsonResults = respObj.getJSONArray("youtube");
                                                if (movieTrailerJsonResults.length() > 0) {
                                                    int randomTrailerId = randomWithRange(0, movieTrailerJsonResults.length() - 1);
                                                    JSONObject trailer = movieTrailerJsonResults.getJSONObject(randomTrailerId);
                                                    String source = trailer.getString("source");
                                                    youTubePlayer.cueVideo(source);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        //}
                                    //});
                                }
                            }
                        });
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider
                                                                provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                    }
                });
}
    int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}

