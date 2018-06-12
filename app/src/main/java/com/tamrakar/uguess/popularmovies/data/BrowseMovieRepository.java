package com.tamrakar.uguess.popularmovies.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tamrakar.uguess.popularmovies.helpers.MovieDbService;
import com.tamrakar.uguess.popularmovies.helpers.MovieDeserializer;
import com.tamrakar.uguess.popularmovies.models.Movie;
import com.tamrakar.uguess.popularmovies.models.Movies;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BrowseMovieRepository {

    //region Variables...
    private static BrowseMovieRepository browseMovieRepository;
    private MovieDbService movieDbService;
    //endregion

    //region Constructors...
    private BrowseMovieRepository() {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(MovieDbService.HTTPS_API_MOVIE_DB_URL)
                .addConverterFactory(buildGsonConverter())
                .build();
        movieDbService = retrofit.create(MovieDbService.class);
    }

    public synchronized static BrowseMovieRepository getInstance() {
        if (browseMovieRepository == null) {
            browseMovieRepository = new BrowseMovieRepository();
        }

        return browseMovieRepository;
    }
    //endregion

    //region Helper Methods...
    private GsonConverterFactory buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Movie.class, new MovieDeserializer());
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }

    public LiveData<Movies> getPopularMovies(String apiKey) {
        final MutableLiveData<Movies> resultMovies = new MutableLiveData<>();

        movieDbService.getPopularMovies(apiKey).enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                resultMovies.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable throwable) {
                resultMovies.setValue(null);
            }
        });
        return resultMovies;
    }

    public LiveData<Movies> getTopRatedMovies(String apiKey) {
        final MutableLiveData<Movies> resultMovies = new MutableLiveData<>();

        movieDbService.getTopRatedMovies(apiKey).enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                resultMovies.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                resultMovies.setValue(null);
            }
        });
        return resultMovies;
    }
    //endregion
}
