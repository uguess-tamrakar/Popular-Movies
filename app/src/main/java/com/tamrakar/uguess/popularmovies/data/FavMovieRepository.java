package com.tamrakar.uguess.popularmovies.data;

import android.app.Application;
import android.app.PictureInPictureParams;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.tamrakar.uguess.popularmovies.models.Movie;

import java.util.List;

public class FavMovieRepository {

    private FavMovieDao mFavMovieDao;
    private LiveData<List<Movie>> mFavMovies;

    public FavMovieRepository(Application application) {
        PopularMoviesDatabase db = PopularMoviesDatabase.getInstance(application);
        mFavMovieDao = db.favMovieDao();
        mFavMovies = mFavMovieDao.loadAllFavMovies();
    }

    public LiveData<List<Movie>> getFavMovies() {
        return mFavMovies;
    }

    public void insert(Movie favMovie) {
        new insertAsyncTask(mFavMovieDao).execute(favMovie);
    }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void>{

        private FavMovieDao mAsyncTaskDao;

        insertAsyncTask(FavMovieDao favMovieDao) {
            mAsyncTaskDao = favMovieDao;
        }

        @Override
        protected Void doInBackground(Movie... favMovies) {
            mAsyncTaskDao.insertFavMovie(favMovies[0]);
            return null;
        }
    }
}