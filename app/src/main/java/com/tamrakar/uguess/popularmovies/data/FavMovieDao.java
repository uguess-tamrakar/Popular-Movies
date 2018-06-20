package com.tamrakar.uguess.popularmovies.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.tamrakar.uguess.popularmovies.models.Movie;

import java.util.List;

@Dao
public interface FavMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavMovie(Movie favMovie);

    @Query("SELECT * FROM fav_movies ORDER BY movieId")
    LiveData<List<Movie>> loadAllFavMovies();

    @Query("SELECT movieId FROM fav_movies WHERE movieId = :movieId")
    int getFavMovieByMovieId(int movieId);

    @Delete
    void deleteFavMovie(Movie favMovie);
}
