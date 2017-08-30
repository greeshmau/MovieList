package com.example.gumapathi.movielist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.gumapathi.movielist.Adapter.MoviesAdapter;
import com.example.gumapathi.movielist.Model.Movie;

import java.util.ArrayList;
import java.util.List;

public class ListMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movies);

        RecyclerView recList = (RecyclerView) findViewById(R.id.rvMovies);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        MoviesAdapter ma = new MoviesAdapter(createMovies(30));
        recList.setAdapter(ma);
    }

    private List<Movie> createMovies(int size) {

        List<Movie> result = new ArrayList<Movie>();
        for (int i=1; i <= size; i++) {
            Movie mi = new Movie("Ttest", String.valueOf(i), "testagain");
            result.add(mi);

        }

        return result;
    }
}
