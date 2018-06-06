package com.tamrakar.uguess.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

    //region Variables...
    public static final String CONTENT_AUTHORITY = "com.tamrakar.uguess.popularmovies.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FAV_MOVIE = "fav_movie";

    public static final class MovieContent implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAV_MOVIE)
                .build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAV_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAV_MOVIE;
        public static final String TABLE_NAME = "movie";
        public static final String COL_ID = "movie_id";
        public static final String COL_TITLE = "title";
    }
    //endregion

    public static Uri buildMovieUri(int id) {
        return ContentUris.withAppendedId(BASE_CONTENT_URI, id);
    }
}
