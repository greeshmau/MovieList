package com.example.gumapathi.movielist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.gumapathi.movielist.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YoutubePlayerActivity extends YouTubeBaseActivity {
    private static String API_KEY = "AIzaSyDZuLakIYICjMX_DlSOEiH6tM9uyLY0N2U";//"AIzaSyAZlHKhCex4OpVSADaDv5ZHDHUTuEBs5MM";
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        Intent intent = getIntent();
        final Long movieId = intent.getLongExtra("movie_id", -1);
        Log.i("SAMY", "movie id: " + movieId);
        if (movieId != -1) {
            youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayer);
            youTubePlayerView.initialize(API_KEY,
                    new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            final YouTubePlayer youTubePlayer, boolean b) {

                            //get video trailer source
                            String url = "https://api.themoviedb.org/3/movie/" + movieId.toString() + "/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

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
                                                youTubePlayer.loadVideo(source);
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
                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                            YouTubeInitializationResult youTubeInitializationResult) {
                        }
                    });
        } else {
            finish();
        }
    }

    int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

}