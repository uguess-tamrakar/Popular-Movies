package com.tamrakar.uguess.popularmovies.helpers;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.tamrakar.uguess.popularmovies.BuildConfig;
import com.tamrakar.uguess.popularmovies.models.Movie;
import com.tamrakar.uguess.popularmovies.models.MovieTrailer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MovieTrailersLoader extends AsyncTaskLoader<ArrayList<MovieTrailer>> {

    //region Variables...
    public static final int MOVIE_TRAILERS_LOADER_ID = 25;
    private static final String LOG_TAG = MovieTrailersLoader.class.getSimpleName();
    private ArrayList<MovieTrailer> mData;
    private String mMovieId;
    //endregion

    //region Constructors...
    public MovieTrailersLoader(Context context, String movieId) {
        super(context);
        mMovieId = movieId;
    }
    //endregion

    //region Overridden Methods...
    @Override
    public ArrayList<MovieTrailer> loadInBackground() {
        ArrayList<MovieTrailer> movieTrailers = new ArrayList<>();

        try {
            String apiKey = BuildConfig.THE_MOVIE_DB_API;
            String uriString = UriHelper.getMovieTrailersUriString(apiKey, mMovieId);
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
                movieTrailers = jsonHelper.parseMovieTrailersJson(responseBuilder.toString());
            } finally {
                httpURLConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.getMessage());
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        return movieTrailers;
    }

    @Override
    public void deliverResult(ArrayList<MovieTrailer> movieTrailers) {
        mData = movieTrailers;
        if (isStarted()) {
            super.deliverResult(movieTrailers);
        } else if (isReset()) {
            return;
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
    //endregion
}
