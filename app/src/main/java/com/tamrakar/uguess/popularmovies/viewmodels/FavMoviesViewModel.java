package com.tamrakar.uguess.popularmovies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.tamrakar.uguess.popularmovies.data.FavMovieRepository;
import com.tamrakar.uguess.popularmovies.models.Movie;

import java.util.List;

public class FavMoviesViewModel extends AndroidViewModel {

    //region Variables...
    private FavMovieRepository mRepository;
    private LiveData<List<Movie>> mFavMovies;
    private LiveData<List<Integer>> mFavMovieIds;
    //endregion

    //region Constructors...
    public FavMoviesViewModel(Application application) {
        super(application);
        mRepository = new FavMovieRepository(application);
        mFavMovies = mRepository.getFavMovies();
        mFavMovieIds = mRepository.getFavMovieIds();
    }
    //endregion

    //region Getters...
    public LiveData<List<Movie>> getFavMovies() {
        return mFavMovies;
    }

    public LiveData<List<Integer>> getmFavMovieIds() {
        return mFavMovieIds;
    }
    //endregion

    //region Methods...
    public void insert(Movie favMovie) {
        mRepository.insert(favMovie);
    }
    //endregion
}
