package com.example.gumapathi.movielist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gumapathi.movielist.Model.Movie;
import com.example.gumapathi.movielist.R;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDetailActivity extends AppCompatActivity {
    TextView tvTitle, tvOverview, tvPopularity;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        int movieId = intent.getIntExtra("movie_id", -1);
        Log.i("SAMY", "movie id: " + movieId);
        if(movieId != -1){
            getMovieDetails(movieId);
        }
    }

    private void getMovieDetails(int movieId) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + movieId +"?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed")
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
                                            singleMoviejson.getDouble("vote_average"),
                                            singleMoviejson.getDouble("popularity"));
                                tvTitle = (TextView) findViewById(R.id.tvTitle);
                                tvOverview = (TextView) findViewById(R.id.tvOverview);
                                tvPopularity = (TextView) findViewById(R.id.tvPopularity);
                                ratingBar = (RatingBar) findViewById(R.id.ratingBar);

                                tvTitle.setText(mi.title);
                                tvOverview.setText(mi.overview);
                                tvPopularity.setText(String.format( "Popularity: %.2f", mi.popularity));

                                //Picasso.with(this).load(movie.getPosterPath()).placeholder(R.mipmap.ic_launcher).transform(new RoundedCornersTransformation(15, 15, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT)).into(ivMovieImage);
                                ratingBar.setNumStars((int)Math.round(mi.vote_average) + 1);
                                //Log.i(TAG, "rating: " + mi.vote_average + " " + mi.vote_average.floatValue());
                                ratingBar.setStepSize((float)0.01);

                                ratingBar.setRating((float)mi.vote_average);

                            } catch (Exception e) {

                            }
                        }
                    });
                }
            }
        });
    }

}
