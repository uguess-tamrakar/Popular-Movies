package com.tamrakar.uguess.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    //region Variables...

    private int mMovieId;
    private String mTitle;
    private String mPosterImagePath;
    private String mBackdropPath;
    private String mOriginalTitle;
    private String mOverview;
    private String mUserRating;
    private String mReleaseDate;

    //endregion

    //region Constructors...

    public Movie() {
    }

    public Movie(int movieId, String title, String posterImagePath, String backdropPath, String originalTitle, String overview, String userRating, String releaseDate) {
        this.mMovieId = movieId;
        this.mTitle = title;
        this.mPosterImagePath = posterImagePath;
        this.mBackdropPath = backdropPath;
        this.mOriginalTitle = originalTitle;
        this.mOverview = overview;
        this.mUserRating = userRating;
        this.mReleaseDate = releaseDate;
    }

    public Movie(Parcel parcel) {
        mMovieId = parcel.readInt();
        mTitle = parcel.readString();
        mPosterImagePath = parcel.readString();
        mBackdropPath = parcel.readString();
        mOriginalTitle = parcel.readString();
        mOverview = parcel.readString();
        mUserRating = parcel.readString();
        mReleaseDate = parcel.readString();
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

    public String getTitle() {
        return mTitle;
    }

    public String getPosterImagePath() {
        return mPosterImagePath;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getUserRating() {
        return mUserRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public int getMovieId() {
        return mMovieId;
    }

    //endregion Getters...

    //region Overridden Methods...
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mMovieId);
        dest.writeString(mTitle);
        dest.writeString(mPosterImagePath);
        dest.writeString(mBackdropPath);
        dest.writeString(mOriginalTitle);
        dest.writeString(mOverview);
        dest.writeString(mUserRating);
        dest.writeString(mReleaseDate);
    }
    //endregion

}
