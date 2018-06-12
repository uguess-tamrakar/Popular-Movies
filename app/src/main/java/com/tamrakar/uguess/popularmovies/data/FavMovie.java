package com.tamrakar.uguess.popularmovies.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

//@Entity(tableName = "fav_movies")
//public class FavMovie {
//
//    //region Variables...
//    @PrimaryKey
//    private int movieId;
//
//    @ColumnInfo(name = "title")
//    private String title;
//
//    @ColumnInfo(name = "posterImagePath")
//    private String posterImagePath;
//
//    @ColumnInfo(name = "backdropPath")
//    private String backdropPath;
//
//    @ColumnInfo(name = "originalTitle")
//    private String originalTitle;
//
//    @ColumnInfo(name = "overview")
//    private String overview;
//
//    @ColumnInfo(name = "userRating")
//    private String userRating;
//
//    @ColumnInfo(name = "releasedDate")
//    public String releasedDate;
//    //endregion
//
//    //region Constructors...
//    public FavMovie() {
////    public FavMovie(int movieId, String title, String posterImagePath,
////                    String backdropPath, String originalTitle, String overview,
////                    String userRating, String releasedDate) {
////        this.movieId = movieId;
////        this.title = title;
////        this.posterImagePath = posterImagePath;
////        this.backdropPath = backdropPath;
////        this.originalTitle = originalTitle;
////        this.overview = overview;
////        this.userRating = userRating;
////        this.releasedDate = releasedDate;
//    }
//    //endregion
//
//    //region Getters...
//    public int getMovieId() {
//        return movieId;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getPosterImagePath() {
//        return posterImagePath;
//    }
//
//    public String getBackdropPath() {
//        return backdropPath;
//    }
//
//    public String getOriginalTitle() {
//        return originalTitle;
//    }
//
//    public String getOverview() {
//        return overview;
//    }
//
//    public String getUserRating() {
//        return userRating;
//    }
//
//    public String getReleasedDate() {
//        return releasedDate;
//    }
//    //endregion
//
//}
