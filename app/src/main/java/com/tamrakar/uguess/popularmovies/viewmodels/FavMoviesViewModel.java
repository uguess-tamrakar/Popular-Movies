package com.tamrakar.uguess.popularmovies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.tamrakar.uguess.popularmovies.data.FavMovieRepository;
import com.tamrakar.uguess.popularmovies.models.Movie;

import java.util.List;

public class FavMoviesViewModel extends AndroidViewModel {

    private FavMovieRepository mRepository;
    private LiveData<List<Movie>> mFavMovies;

    public FavMoviesViewModel(Application application) {
        super(application);
        mRepository = new FavMovieRepository(application);
        mFavMovies = mRepository.getFavMovies();
    }

    public LiveData<List<Movie>> getFavMovies() {
        return mFavMovies;
    }

    public void insert(Movie favMovie) {
        mRepository.insert(favMovie);
    }
}
