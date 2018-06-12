package com.tamrakar.uguess.popularmovies.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movies {
    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }
}
