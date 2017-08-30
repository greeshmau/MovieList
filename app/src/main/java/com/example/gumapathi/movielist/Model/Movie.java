package com.example.gumapathi.movielist.Model;

import java.util.ArrayList;

/**
 * Created by gumapathi on 8/30/2017.
 */

public class Movie {
    public int vote_count;
    public int id;
    public boolean video;
    public float vote_average;
    public String title;
    public float popularity;
    public String poster_path;
    public String original_language;
    public String original_title;
    public String backdrop_path;
    public boolean adult;
    public String overview;
    public String release_date;

    public Movie(String title, String  overview, String image) {
        this.title = title;
        this.overview = overview;
        this.poster_path = image;
    }
    /*private static int lastMovieId = 0;
    public static ArrayList<Movie> createContactsList(int numOfMovies) {
        ArrayList<Movie> contacts = new ArrayList<Movie>();

        for (int i = 1; i <= numOfMovies; i++) {
            contacts.add(new Movie("Movie " + ++lastMovieId, lastMovieId, "test"));
        }

        return contacts;
    }*/
}
