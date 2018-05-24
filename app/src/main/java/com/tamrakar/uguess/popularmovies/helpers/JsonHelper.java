package com.tamrakar.uguess.popularmovies.helpers;

import android.util.Log;

import com.tamrakar.uguess.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonHelper {

    private static String LOG_TAG = JsonHelper.class.getName();

    public ArrayList<Movie> parseMoviesJson(String json) {
        ArrayList<Movie> movies = new ArrayList<>();

        try {

            if (json != null) {
                JSONObject jsonObject = new JSONObject(json);

                JSONArray resultsArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject resultJsonObj = resultsArray.getJSONObject(i);

                    if (resultJsonObj != null) {
                        String title = resultJsonObj.optString("title");
                        String posterPath = resultJsonObj.optString("poster_path");
                        String backdropPath = resultJsonObj.optString("backdrop_path");
                        String originalTitle = resultJsonObj.optString("original_title");
                        String overview = resultJsonObj.optString("overview");
                        String userRating = resultJsonObj.optString("vote_average");
                        String releaseDate = resultJsonObj.optString("release_date");

                        movies.add(new Movie(title, posterPath, backdropPath, originalTitle, overview, userRating, releaseDate));
                    }
                }
            }

        } catch (JSONException ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }

        return movies;
    }
}
