package com.tamrakar.uguess.popularmovies.utils;

public class UriUtils {

    private static final String MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3";
    private static final String TMDB_IMAGE_BASE_URI = "http://image.tmdb.org/t/p/w185/";
    private static final String POPULAR_ENDPOINT = "/movie/popular";
    private static final String TOP_RATED_ENDPOINT = "/move/top_rated";
    private static final String API_KEY_PARAM = "?api_key";

    public String getPopularMoviesUriString(String apiKey) throws Exception {
        if (!apiKey.isEmpty()) {
            StringBuilder result = new StringBuilder();
            result.append(MOVIE_DB_BASE_URL).append(POPULAR_ENDPOINT).append(API_KEY_PARAM).append("=" + apiKey);
            return result.toString();
        } else {
            throw new Exception("Api key is required.");
        }
    }

    public String getTopRatedMoviesUriString(String apiKey) throws Exception {
        if (!apiKey.isEmpty()) {
            StringBuilder result = new StringBuilder();
            result.append(MOVIE_DB_BASE_URL).append(TOP_RATED_ENDPOINT).append(API_KEY_PARAM).append("=" + apiKey);
            return result.toString();
        } else {
            throw new Exception("Api key is required.");
        }
    }

    public String getTmdbImageUriString(String posterPath) throws Exception {
        if (!posterPath.isEmpty()) {
            StringBuilder result = new StringBuilder();
            result.append(TMDB_IMAGE_BASE_URI).append(posterPath);
            return result.toString();
        } else {
            throw new Exception("Api key is required.");
        }
    }
}
