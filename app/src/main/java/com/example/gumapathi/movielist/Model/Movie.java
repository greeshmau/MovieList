package com.example.gumapathi.movielist.Model;

import java.util.ArrayList;

/**
 * Created by gumapathi on 8/30/2017.
 */

public class Movie {
    public int vote_count;
    public int movie_id;
    public boolean video;
    public double vote_average;
    public String title;
    public double popularity;
    public String poster_path;
    public String original_language;
    public String backdrop_path;
    public boolean adult;
    public String overview;
    public String release_date;

    public Movie(int movie_id,
                 String title,
                 String  overview,
                 String poster_path,
                 String backdrop_path,
                 double vote_average,
                 double popularity) {
        this.movie_id = movie_id;
        this.title = title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.vote_average = vote_average;
        this.popularity = popularity;
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
