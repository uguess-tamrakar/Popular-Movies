package com.tamrakar.uguess.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //region Variables...
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "movies.db";
    //endregion

    //region Constructors...
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    //endregion

    //region Overridden Methods...
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create movie table
        final String CREATE_MOVIE_TABLE_SQL = "CREATE TABLE " +
                MovieContract.MovieContent.TABLE_NAME +
                " (" + MovieContract.MovieContent.COL_ID +
                " INTEGER PRIMARY KEY," + MovieContract.MovieContent.COL_TITLE +
                " TEXT NOT NULL" + " );";
        db.execSQL(CREATE_MOVIE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieContent.TABLE_NAME);
        onCreate(db);
    }
    //endregion

}
