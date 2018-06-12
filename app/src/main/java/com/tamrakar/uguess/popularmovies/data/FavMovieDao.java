package com.tamrakar.uguess.popularmovies.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.tamrakar.uguess.popularmovies.models.Movie;

import java.util.List;

@Dao
public interface FavMovieDao {

    @Insert
    void insertFavMovie(Movie favMovie);

    @Query("SELECT * FROM fav_movies ORDER BY movieId")
    LiveData<List<Movie>> loadAllFavMovies();

    @Delete
    void deleteFavMovie(Movie favMovie);
}
