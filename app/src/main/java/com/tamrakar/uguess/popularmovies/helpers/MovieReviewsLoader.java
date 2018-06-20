package com.tamrakar.uguess.popularmovies.helpers;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.tamrakar.uguess.popularmovies.BuildConfig;
import com.tamrakar.uguess.popularmovies.models.MovieReview;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MovieReviewsLoader extends AsyncTaskLoader<ArrayList<MovieReview>> {

    public static final int MOVIE_REVIEWS_LOADER_ID = 2525;
    private static final String LOG_TAG = MovieReviewsLoader.class.getSimpleName();
    private ArrayList<MovieReview> mData;
    private String mMovieId;

    public MovieReviewsLoader(Context context, String movieId) {
        super(context);
        mMovieId = movieId;
    }


    @Override
    public ArrayList<MovieReview> loadInBackground() {
        ArrayList<MovieReview> movieReviews = new ArrayList<>();

        try {
            String apiKey = BuildConfig.THE_MOVIE_DB_API;
            String uriString = UriHelper.getMovieReviewsUriString(apiKey, mMovieId);
            URL url = new URL(uriString);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            try {
                //Read the input stream into buffer
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String responseLine;

                //Read the buffer line by line and append to responseBuilder
                while (null != (responseLine = bufferedReader.readLine())) {
                    responseBuilder.append(responseLine).append("\n");
                }

                bufferedReader.close();

                JsonHelper jsonHelper = new JsonHelper();
                movieReviews = jsonHelper.parseMovieReviewsJson(responseBuilder.toString());
            } finally {
                httpURLConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.getMessage());
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        return movieReviews;
    }

    @Override
    public void deliverResult(ArrayList<MovieReview> movieReviews) {
        mData = movieReviews;
        if (isStarted()) {
            super.deliverResult(movieReviews);
        } else if (isReset()) {
        }
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        } else {
            forceLoad();
        }
    }
}
