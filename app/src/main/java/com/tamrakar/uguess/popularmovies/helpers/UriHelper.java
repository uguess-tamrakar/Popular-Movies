package com.tamrakar.uguess.popularmovies.helpers;

public class UriHelper {

    //region Static Variables...
    private static final String MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3";
    private static final String TMDB_IMAGE_BASE_URI = "http://image.tmdb.org/t/p/";
    private static final String POPULAR_ENDPOINT = "/movie/popular";
    private static final String TOP_RATED_ENDPOINT = "/movie/top_rated";
    private static final String VIDEOS_ENDPOINT = "/movie/{movie_id}/videos";
    private static final String REVIEWS_ENDPOINT = "/movie/{movie_id}/reviews";
    private static final String API_KEY_PARAM = "?api_key";
    //endregion

    //region Static Methods...
//    public static String getPopularMoviesUriString(String apiKey) throws Exception {
//        if (!apiKey.isEmpty()) {
//            StringBuilder result = new StringBuilder();
//            result.append(MOVIE_DB_BASE_URL)
//                    .append(POPULAR_ENDPOINT)
//                    .append(API_KEY_PARAM).append("=" + apiKey);
//            return result.toString();
//        } else {
//            throw new Exception("Api key is required.");
//        }
//    }
//
//    public static String getTopRatedMoviesUriString(String apiKey) throws Exception {
//        if (!apiKey.isEmpty()) {
//            StringBuilder result = new StringBuilder();
//            result.append(MOVIE_DB_BASE_URL)
//                    .append(TOP_RATED_ENDPOINT)
//                    .append(API_KEY_PARAM)
//                    .append("=" + apiKey);
//            return result.toString();
//        } else {
//            throw new Exception("Api key is required.");
//        }
//    }

    public static String getMovieTrailersUriString(String apiKey, String movieId) throws Exception {
        if (!movieId.isEmpty() && !apiKey.isEmpty()) {
            StringBuilder result = new StringBuilder();
            result.append(MOVIE_DB_BASE_URL)
                    .append(VIDEOS_ENDPOINT.replace("{movie_id}", movieId))
                    .append(API_KEY_PARAM)
                    .append("=" + apiKey);
            return result.toString();
        } else {
            throw new Exception("Api key and movieId are required.");
        }
    }

    public static String getMovieReviewsUriString(String apiKey, String movieId) throws Exception {
        if (!movieId.isEmpty() && !apiKey.isEmpty()) {
            StringBuilder result = new StringBuilder();
            result.append(MOVIE_DB_BASE_URL)
                    .append(REVIEWS_ENDPOINT.replace("{movie_id}", movieId))
                    .append(API_KEY_PARAM)
                    .append("=" + apiKey);
            return result.toString();
        } else {
            throw new Exception("Api key and movieId are required.");
        }
    }

    public static String getTmdbMoviePosterUriString(String posterPath) throws Exception {
        if (!posterPath.isEmpty()) {
            StringBuilder result = new StringBuilder();
            result.append(TMDB_IMAGE_BASE_URI).append("w185").append(posterPath);
            return result.toString();
        } else {
            throw new Exception("Api key is required.");
        }
    }

    public static String getTmdbMovieBackdropUriString(String posterPath) throws Exception {
        if (!posterPath.isEmpty()) {
            StringBuilder result = new StringBuilder();
            result.append(TMDB_IMAGE_BASE_URI).append("w342").append(posterPath);
            return result.toString();
        } else {
            throw new Exception("Api key is required.");
        }
    }
    //endregion
}
