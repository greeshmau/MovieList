package com.example.gumapathi.movielist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.gumapathi.movielist.Adapter.MoviesAdapter;
import com.example.gumapathi.movielist.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListMoviesActivity extends AppCompatActivity {

    RecyclerView recList;
    MoviesAdapter ma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movies);

        RecyclerView recList = (RecyclerView) findViewById(R.id.rvMovies);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        createMovies();

        /*MoviesAdapter ma = new MoviesAdapter(createMovies());
        recList.setAdapter(ma);
        ma.notifyDataSetChanged();*/
    }

    private void createMovies() {
        OkHttpClient client = new OkHttpClient();
        String sample;

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed")
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
                }
                else {
                         runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String responseData = response.body().string();
                                    JSONObject json = new JSONObject(responseData);
                                    JSONArray movieResults = json.getJSONArray("results");
                                    List<Movie> result = new ArrayList<Movie>();
                                    for (int i = 0; i < movieResults.length(); i++) {
                                        JSONObject singleMovie = movieResults.getJSONObject(i);
                                        Movie mi = new Movie(singleMovie.getInt("id"),
                                                singleMovie.getString("original_title"),
                                                singleMovie.getString("overview"),
                                                singleMovie.getString("poster_path"),
                                                singleMovie.getString("backdrop_path"),
                                                singleMovie.getDouble("popularity"));
                                        //Log.i("SAMY", mi.title);
                                        result.add(mi);
                                    }
                                    ma = new MoviesAdapter(result);
                                    RecyclerView recList = (RecyclerView) findViewById(R.id.rvMovies);
                                    recList.setAdapter(ma);
                                    ma.notifyDataSetChanged();
                                }
                                catch (Exception e) {

                                }
                            }
                        });
                }
            }
        });
    }
}
