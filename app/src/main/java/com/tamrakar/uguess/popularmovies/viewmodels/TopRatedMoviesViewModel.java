package com.tamrakar.uguess.popularmovies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.tamrakar.uguess.popularmovies.BuildConfig;
import com.tamrakar.uguess.popularmovies.data.BrowseMovieRepository;
import com.tamrakar.uguess.popularmovies.models.Movies;

public class TopRatedMoviesViewModel extends AndroidViewModel {
    private final LiveData<Movies> observableMovies;

    public TopRatedMoviesViewModel(@NonNull Application application) {
        super(application);
        String apiKey = BuildConfig.THE_MOVIE_DB_API;
        observableMovies = BrowseMovieRepository.getInstance().getTopRatedMovies(apiKey);
    }

    public LiveData<Movies> getObservableMovies() {
        return observableMovies;
    }
}
