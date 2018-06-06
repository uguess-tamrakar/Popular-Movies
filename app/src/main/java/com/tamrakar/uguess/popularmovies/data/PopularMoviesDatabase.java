package com.tamrakar.uguess.popularmovies.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.tamrakar.uguess.popularmovies.models.Movie;

@Database(entities = {FavMovieEntity.class}, version = 1, exportSchema = false)
public abstract class PopularMoviesDatabase extends RoomDatabase {

    private static final String LOG_TAG = PopularMoviesDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "popularmovies";
    private static PopularMoviesDatabase INSTANCE;

    public static PopularMoviesDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating a new PopularMovies database instance...");
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        PopularMoviesDatabase.class, PopularMoviesDatabase.DATABASE_NAME)
                        .build();
            }
        }

        Log.d(LOG_TAG, "Getting a PopularMovies database instance...");
        return INSTANCE;
    }

    public abstract FavMovieDao favMovieDao();
}
