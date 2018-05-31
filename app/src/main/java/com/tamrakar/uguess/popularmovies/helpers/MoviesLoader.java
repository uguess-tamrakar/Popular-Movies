package com.tamrakar.uguess.popularmovies.helpers;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.tamrakar.uguess.popularmovies.model.Movie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MoviesLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    public static final int MOVIES_LOADER_ID = 25;
    private static final String LOG_TAG = MoviesLoader.class.getSimpleName();

    private int mSortOrder;
    private ArrayList<Movie> mData;

    public MoviesLoader(Context context, int sortOrder) {
        super(context);
        mSortOrder = sortOrder;
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            UriHelper uriHelper = new UriHelper();
            String uriString = (mSortOrder == 0) ?
                    uriHelper.getPopularMoviesUriString("77ea09a490e5a4a8bed60fbc1bf1c716") :
                    uriHelper.getTopRatedMoviesUriString("77ea09a490e5a4a8bed60fbc1bf1c716");
            URL url = new URL(uriString);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            try {
                //Read the input stream into buffer
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String responseLine;

                //Read the buffer line by line and append to responseBuilder
                while (null != (responseLine = bufferedReader.readLine())) {
                    responseBuilder.append(responseLine).append("\n");
                }

                bufferedReader.close();

                JsonHelper jsonHelper = new JsonHelper();
                movies = jsonHelper.parseMoviesJson(responseBuilder.toString());
            } finally {
                httpURLConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.getMessage());
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        return movies;
    }

    @Override
    public void deliverResult(ArrayList<Movie> movies) {

        mData = movies;

        if (isStarted()) {
            super.deliverResult(movies);
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
}
