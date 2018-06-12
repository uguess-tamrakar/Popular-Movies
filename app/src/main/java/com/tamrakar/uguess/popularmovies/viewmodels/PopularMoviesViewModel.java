package com.tamrakar.uguess.popularmovies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.tamrakar.uguess.popularmovies.BuildConfig;
import com.tamrakar.uguess.popularmovies.data.BrowseMovieRepository;
import com.tamrakar.uguess.popularmovies.models.Movie;
import com.tamrakar.uguess.popularmovies.models.Movies;

import java.util.List;

public class PopularMoviesViewModel extends AndroidViewModel {

    private final LiveData<Movies> observableMovies;

    public PopularMoviesViewModel(@NonNull Application application) {
        super(application);
        String apiKey = BuildConfig.THE_MOVIE_DB_API;
        observableMovies = BrowseMovieRepository.getInstance().getPopularMovies(apiKey);
    }

    public LiveData<Movies> getObservableMovies() {
        return observableMovies;
    }
}
