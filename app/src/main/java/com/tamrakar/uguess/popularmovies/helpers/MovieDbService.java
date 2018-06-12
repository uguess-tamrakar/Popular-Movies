package com.tamrakar.uguess.popularmovies.helpers;

import com.tamrakar.uguess.popularmovies.models.Movie;
import com.tamrakar.uguess.popularmovies.models.Movies;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDbService {
    String HTTPS_API_MOVIE_DB_URL = "http://api.themoviedb.org/3/";

    @GET("movie/popular")
    Call<Movies> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<Movies> getTopRatedMovies(@Query("api_key") String apiKey);
}
