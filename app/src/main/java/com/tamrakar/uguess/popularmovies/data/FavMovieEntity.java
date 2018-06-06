package com.tamrakar.uguess.popularmovies.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "fav_movies")
public class FavMovieEntity {

    //region Variables...
    @PrimaryKey(autoGenerate = true)
    private int movieId;
    @ColumnInfo(name = "title")
    private String title;
    //endregion

    //region Constructors...
    @Ignore
    public FavMovieEntity(String title) {
        this.title = title;
    }

    public FavMovieEntity(int movieId, String title) {
        this.movieId = movieId;
        this.title = title;
    }
    //endregion

    //region Getters...
    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }
    //endregion

}
