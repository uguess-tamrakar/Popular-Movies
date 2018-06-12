package com.tamrakar.uguess.popularmovies.helpers;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.tamrakar.uguess.popularmovies.models.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MovieDeserializer implements JsonDeserializer<Movie> {

    private final static String LOG_TAG = MovieDeserializer.class.getSimpleName();

    @Override
    public Movie deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Movie resultMovie = null;

        try {

            if (json != null) {
                JSONObject jsonObject = new JSONObject(json.getAsJsonObject().toString());

                if (jsonObject != null) {
                    int movieId = jsonObject.getInt("id");
                    String title = jsonObject.optString("title");
                    String posterPath = jsonObject.optString("poster_path");
                    String backdropPath = jsonObject.optString("backdrop_path");
                    String originalTitle = jsonObject.optString("original_title");
                    String overview = jsonObject.optString("overview");
                    String userRating = jsonObject.optString("vote_average");
                    String releaseDate = jsonObject.optString("release_date");

                    resultMovie = new Movie(movieId, title, posterPath, backdropPath, originalTitle, overview, userRating, releaseDate);
                }
            }
        } catch (JSONException ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }

        return resultMovie;
    }
}
