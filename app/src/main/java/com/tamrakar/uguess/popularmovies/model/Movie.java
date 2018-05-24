package com.tamrakar.uguess.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String title;
    private String posterImagePath;
    private String backdropPath;
    private String originalTitle;
    private String overview;
    private String userRating;
    private String releaseDate;

    /**
     * No args constructor for use in serialization
     */
    public Movie() {
    }

    public Movie(String title, String posterImagePath, String backdropPath, String originalTitle, String overview, String userRating, String releaseDate) {
        this.title = title;
        this.posterImagePath = posterImagePath;
        this.backdropPath = backdropPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    public Movie(Parcel parcel) {
        title = parcel.readString();
        posterImagePath = parcel.readString();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String originalTitle) {
        this.title = originalTitle;
    }

    public String getPosterImagePath() {
        return posterImagePath;
    }

    public void setPosterImagePath(String posterImagePath) {
        this.posterImagePath = posterImagePath;
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

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(posterImagePath);
        dest.writeString(overview);
        dest.writeString(userRating);
        dest.writeString(releaseDate);
    }
}
