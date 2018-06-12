package com.tamrakar.uguess.popularmovies.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

@Entity(tableName = "fav_movies")
public class Movie implements Parcelable {

    //region Variables...
    @PrimaryKey
    private int movieId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "posterImagePath")
    private String posterImagePath;

    @ColumnInfo(name = "backdropPath")
    private String backdropPath;

    @ColumnInfo(name = "originalTitle")
    private String originalTitle;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "userRating")
    private String userRating;

    @ColumnInfo(name = "releasedDate")
    public String releaseDate;

    @Ignore
    public List<MovieTrailer> movieTrailers;
    //endregion

    //region Constructors...

    @Ignore
    public Movie() {
    }

    public Movie(int movieId, String title, String posterImagePath, String backdropPath, String originalTitle, String overview, String userRating, String releaseDate) {
        this.movieId = movieId;
        this.title = title;
        this.posterImagePath = posterImagePath;
        this.backdropPath = backdropPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    @Ignore
    public Movie(int movieId, String title, String posterImagePath, String backdropPath, String originalTitle, String overview, String userRating, String releaseDate, List<MovieTrailer> movieTrailers) {
        this.movieId = movieId;
        this.title = title;
        this.posterImagePath = posterImagePath;
        this.backdropPath = backdropPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.movieTrailers = movieTrailers;
    }

    @Ignore
    public Movie(Parcel parcel) {
        movieId = parcel.readInt();
        title = parcel.readString();
        posterImagePath = parcel.readString();
        backdropPath = parcel.readString();
        originalTitle = parcel.readString();
        overview = parcel.readString();
        userRating = parcel.readString();
        releaseDate = parcel.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    //endregion Constructors...

    //region Getters...

    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterImagePath() {
        return posterImagePath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<MovieTrailer> getMovieTrailers() {
        return movieTrailers;
    }
    //endregion Getters...

    //region Overridden Methods...
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeString(title);
        dest.writeString(posterImagePath);
        dest.writeString(backdropPath);
        dest.writeString(originalTitle);
        dest.writeString(overview);

        dest.writeString(userRating);
        dest.writeString(releaseDate);
    }
    //endregion

}
