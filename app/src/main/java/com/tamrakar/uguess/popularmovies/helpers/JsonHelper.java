package com.tamrakar.uguess.popularmovies.helpers;

import android.util.Log;

import com.tamrakar.uguess.popularmovies.models.Movie;
import com.tamrakar.uguess.popularmovies.models.MovieReview;
import com.tamrakar.uguess.popularmovies.models.MovieTrailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;

public class JsonHelper {

    private static String LOG_TAG = JsonHelper.class.getName();

    public ArrayList<MovieTrailer> parseMovieTrailersJson(String json) {
        ArrayList<MovieTrailer> movieTrailers = new ArrayList<>();

        try {

            if (json != null) {
                JSONObject jsonObject = new JSONObject(json);

                JSONArray resultsArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject resultJsonObj = resultsArray.getJSONObject(i);

                    if (resultJsonObj != null) {
                        String key = resultJsonObj.optString("key");
                        String id = resultJsonObj.optString("id");
                        String name = resultJsonObj.optString("name");

                        movieTrailers.add(new MovieTrailer(key, id, name));
                    }
                }
            }

        } catch (JSONException ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }

        return movieTrailers;
    }

    public ArrayList<MovieReview> parseMovieReviewsJson(String json) {
        ArrayList<MovieReview> movieReviews = new ArrayList<>();

        try {

            if (json != null) {
                JSONObject jsonObject = new JSONObject(json);

                JSONArray resultsArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject resultJsonObj = resultsArray.getJSONObject(i);

                    if (resultJsonObj != null) {
                        String id = resultJsonObj.optString("id");
                        String author = resultJsonObj.optString("author");
                        String content = resultJsonObj.optString("content");

                        movieReviews.add(new MovieReview(id, author, content));
                    }
                }
            }

        } catch (JSONException ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }

        return movieReviews;
    }
}
