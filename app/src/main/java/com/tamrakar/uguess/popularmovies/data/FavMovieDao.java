package com.tamrakar.uguess.popularmovies.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavMovieDao {

    @Insert
    void insertFavMovie(FavMovieEntity favMovieEntity);

    @Query("SELECT * FROM fav_movies ORDER BY movieId")
    List<FavMovieEntity> loadAllFavMovies();

    @Delete
    void deleteFavMovie(FavMovieEntity favMovieEntity);
}
