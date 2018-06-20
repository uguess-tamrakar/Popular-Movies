package com.tamrakar.uguess.popularmovies.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.tamrakar.uguess.popularmovies.models.Movie;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavMovieRepository {

    //region Variables...
    private FavMovieDao mFavMovieDao;
    private LiveData<List<Movie>> mFavMovies;
    //endregion

    //region Constructors...
    public FavMovieRepository(Application application) {
        PopularMoviesDatabase db = PopularMoviesDatabase.getInstance(application);
        mFavMovieDao = db.favMovieDao();
        mFavMovies = mFavMovieDao.loadAllFavMovies();
    }
    //endregion

    //region Getters...
    public LiveData<List<Movie>> getFavMovies() {
        return mFavMovies;
    }

    public boolean containsFavMovie(Integer movieId) {
        try {
            return new getMovieIdAsyncTask(mFavMovieDao).execute(movieId).get() != 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }
    //endregion

    //region Methods...
    public void delete(Movie favMovie) {
        new deleteAsyncTask(mFavMovieDao).execute(favMovie);
    }

    public void insert(Movie favMovie) {
        new insertAsyncTask(mFavMovieDao).execute(favMovie);
    }
    //endregion

    //region deleteAsyncTask
    private static class deleteAsyncTask extends AsyncTask<Movie, Void, Void> {

        private FavMovieDao mAsyncTaskDao;

        deleteAsyncTask(FavMovieDao favMovieDao) {
            mAsyncTaskDao = favMovieDao;
        }

        @Override
        protected Void doInBackground(Movie... favMovies) {
            mAsyncTaskDao.deleteFavMovie(favMovies[0]);
            return null;
        }
    }
    //endregion

    //region insertAsyncTask
    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

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
    //endregion

    //region getMovieIdAsyncTask
    private static class getMovieIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private FavMovieDao mAsyncTaskDao;

        getMovieIdAsyncTask(FavMovieDao favMovieDao) {
            mAsyncTaskDao = favMovieDao;
        }

        @Override
        protected Integer doInBackground(Integer... movieIds) {
            return mAsyncTaskDao.getFavMovieByMovieId(movieIds[0]);
        }
    }
    //endregion
}
