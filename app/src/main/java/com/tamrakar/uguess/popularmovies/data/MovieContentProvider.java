package com.tamrakar.uguess.popularmovies.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MovieContentProvider extends ContentProvider {

    //region Variables...
    static final int FAV_MOVIE = 100;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final SQLiteQueryBuilder sFavoriteMoviesQueryBuilder = new SQLiteQueryBuilder();
    private DatabaseHelper mDbHelper;

    //endregion

    //region Helper Methods...
    private static UriMatcher buildUriMatcher() {
        final UriMatcher result = new UriMatcher(UriMatcher.NO_MATCH);
        result.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_FAV_MOVIE, FAV_MOVIE);
        return result;
    }

    private Cursor getFavoriteMovies(Uri uri, String[] projection, String sortOrder) {
        return sFavoriteMoviesQueryBuilder.query(mDbHelper.getReadableDatabase(),
                projection,
                null,
                null,
                null,
                null,
                sortOrder);
    }
    //endregion

    //region Overridden Methods...
    @Override
    public boolean onCreate() {
        mDbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor qCursor;

        switch (sUriMatcher.match(uri)) {
            case FAV_MOVIE:
                qCursor = getFavoriteMovies(uri, projection, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Uri could not be resolved: " + uri);
        }

        qCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return qCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int uriMatch = sUriMatcher.match(uri);

        switch (uriMatch) {
            case FAV_MOVIE:
                return MovieContract.CONTENT_AUTHORITY;
            default:
                throw new UnsupportedOperationException("Uri could not be resolved: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int uriMatch = sUriMatcher.match(uri);
        Uri result;

        switch (uriMatch) {
            case FAV_MOVIE:
                int movie_id = (int) db.insert(MovieContract.MovieContent.TABLE_NAME, null, values);

                if (movie_id > 0) {
                    result = MovieContract.buildMovieUri(movie_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }

                break;
            default:
                throw new UnsupportedOperationException("Uri could not be resolved: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int uriMatch = sUriMatcher.match(uri);
        int deletedRowsCount;

        switch (uriMatch) {
            case FAV_MOVIE:
                deletedRowsCount = db.delete(MovieContract.MovieContent.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Uri could not be resolved: " + uri);
        }

        if (deletedRowsCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deletedRowsCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int uriMatch = sUriMatcher.match(uri);
        int updatedRowsCount;

        switch (uriMatch) {
            case FAV_MOVIE:
                updatedRowsCount = db.update(MovieContract.MovieContent.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Uri could not be resolved: " + uri);
        }

        return updatedRowsCount;
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mDbHelper.close();
        super.shutdown();
    }

    //endregion
}
