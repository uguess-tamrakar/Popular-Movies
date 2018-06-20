package com.tamrakar.uguess.popularmovies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.tamrakar.uguess.popularmovies.data.FavMovieRepository;
import com.tamrakar.uguess.popularmovies.models.Movie;

import java.util.List;

public class FavMoviesViewModel extends AndroidViewModel {

    //region Variables...
    private FavMovieRepository mRepository;
    private LiveData<List<Movie>> mFavMovies;
    //endregion

    //region Constructors...
    public FavMoviesViewModel(Application application) {
        super(application);
        mRepository = new FavMovieRepository(application);
        mFavMovies = mRepository.getFavMovies();
    }
    //endregion

    //region Getters...
    public LiveData<List<Movie>> getFavMovies() {
        return mFavMovies;
    }


    //endregion

    //region Methods...

    public void delete(Movie favMovie) {
        mRepository.delete(favMovie);
    }

    public boolean doesContainFavMovie(int movieId) {
        return mRepository.containsFavMovie(movieId);
    }

    public void insert(Movie favMovie) {
        mRepository.insert(favMovie);
    }
    //endregion
}
