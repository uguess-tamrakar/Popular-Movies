package com.tamrakar.uguess.popularmovies.helpers;

public class UriHelper {

    //region Static Variables...
    private static final String MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3";
    private static final String TMDB_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/%s/mqdefault.jpg";
    private static final String YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=%s";
    private static final String VIDEOS_ENDPOINT = "/movie/%s/videos";
    private static final String REVIEWS_ENDPOINT = "/movie/%s/reviews";
    private static final String API_KEY_PARAM = "?api_key";
    //endregion

    //region Static Methods...

    public static String getMovieTrailersUriString(String apiKey, String movieId) throws Exception {
        if (!movieId.isEmpty() && !apiKey.isEmpty()) {
            return MOVIE_DB_BASE_URL +
                    String.format(VIDEOS_ENDPOINT, movieId) +
                    API_KEY_PARAM +
                    "=" + apiKey;
        } else {
            throw new Exception("Api key and movieId are required.");
        }
    }

    public static String getMovieReviewsUriString(String apiKey, String movieId) throws Exception {
        if (!movieId.isEmpty() && !apiKey.isEmpty()) {
            return MOVIE_DB_BASE_URL +
                    String.format(REVIEWS_ENDPOINT, movieId) +
                    API_KEY_PARAM +
                    "=" + apiKey;
        } else {
            throw new Exception("Api key and movieId are required.");
        }
    }

    public static String getYoutTubeThumbnailUriString(String movieVideoKey) throws Exception {
        if (!movieVideoKey.isEmpty()) {
            return String.format(YOUTUBE_THUMBNAIL_URL, movieVideoKey);
        } else {
            throw new Exception("Movie video key is required.");
        }
    }

    public static String getYoutTubeVideoUriString(String movieVideoKey) throws Exception {
        if (!movieVideoKey.isEmpty()) {
            return String.format(YOUTUBE_VIDEO_URL, movieVideoKey);
        } else {
            throw new Exception("Movie video key is required.");
        }
    }

    public static String getTmdbMoviePosterUriString(String posterPath) throws Exception {
        if (!posterPath.isEmpty()) {
            return TMDB_IMAGE_BASE_URL + "w342" + posterPath;
        } else {
            throw new Exception("Api key is required.");
        }
    }

    public static String getTmdbMovieBackdropUriString(String posterPath) throws Exception {
        if (!posterPath.isEmpty()) {
            return TMDB_IMAGE_BASE_URL + "w500" + posterPath;
        } else {
            throw new Exception("Api key is required.");
        }
    }
    //endregion
}
