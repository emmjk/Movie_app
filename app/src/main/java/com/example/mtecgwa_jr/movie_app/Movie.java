package com.example.mtecgwa_jr.movie_app;

/**
 * Created by mtecgwa-jr on 4/13/17.
 */

public class Movie {

    private String cover_url , movieName;
    private long movieId;

    public Movie(String cover_url , String movieName , long movieId)
    {
        this.cover_url = cover_url;

        this.movieName = movieName;

        this.movieId = movieId;


    }

    public String getCover_url()
    {
        return cover_url;
    }

    public String getMovieName()
    {
        return movieName;
    }

    public long getMovieId()
    {
        return movieId;
    }
}
