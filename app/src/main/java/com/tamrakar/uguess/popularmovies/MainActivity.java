package com.tamrakar.uguess.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.tamrakar.uguess.popularmovies.helpers.MoviesLoader;
import com.tamrakar.uguess.popularmovies.helpers.NetworkHelper;
import com.tamrakar.uguess.popularmovies.model.Movie;

import java.util.ArrayList;

import static com.tamrakar.uguess.popularmovies.helpers.MoviesLoader.MOVIES_LOADER_ID;

public class MainActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener,
        LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    private static final String KEY_PREF_SORT_ORDER = "sort_order";

    private static ArrayList<Movie> sMovieByPopularity;
    private static ArrayList<Movie> sMovieByTopRated;
    private SharedPreferences mSharedPreferences;
    private int mSortOrder = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean needToRetrieveMovies = false;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
        PreferenceManager.setDefaultValues(this, R.xml.preferences_main, false);

        mSortOrder = Integer.valueOf(mSharedPreferences.getString(KEY_PREF_SORT_ORDER, ""));

        if (mSortOrder == 0) {
            if (sMovieByPopularity == null) {
                if (savedInstanceState != null) {
                    sMovieByPopularity = savedInstanceState.getParcelableArrayList("movies_by_popularity");
                } else {
                    needToRetrieveMovies = true;
                }

                if (sMovieByPopularity == null) {
                    needToRetrieveMovies = true;
                }
            }

        } else if (mSortOrder == 1) {
            if (sMovieByTopRated == null) {
                if (savedInstanceState != null) {
                    sMovieByTopRated = savedInstanceState.getParcelableArrayList("movies_by_top_rated");
                } else {
                    needToRetrieveMovies = true;
                }

                if (sMovieByTopRated == null) {
                    needToRetrieveMovies = true;
                }
            }
        }

        if (needToRetrieveMovies) {

            if (NetworkHelper.isConnectedToInternet(this)) {
                //Get the list of movies in asynchronous task
                getSupportLoaderManager().initLoader(MOVIES_LOADER_ID, null, this);
            } else {
                Toast.makeText(this, "Not connected to internet.", Toast.LENGTH_LONG).show();
            }
        } else {
            if (mSortOrder == 0) {
                constructMoviesGridView(sMovieByPopularity);
            } else if (mSortOrder == 1) {
                constructMoviesGridView(sMovieByTopRated);
            }
        }

        setMainTitle();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_preferences) {
            startActivity(new Intent(this, PreferencesActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getResources().getString(R.string.key_pref_sort_order))) {
            setMainTitle();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies_by_popularity", sMovieByPopularity);
        outState.putParcelableArrayList("movies_by_top_rated", sMovieByTopRated);
        super.onSaveInstanceState(outState);
    }

    private void constructMoviesGridView(ArrayList<Movie> movies) {
        if (movies != null && !movies.isEmpty()) {
            GridView gvMain = findViewById(R.id.gv_main);
            MoviesAdapter moviesAdapter = new MoviesAdapter(this, movies);
            gvMain.setAdapter(moviesAdapter);
        } else {
            Toast.makeText(this, "Movies list is empty. Check your internet connection.", Toast.LENGTH_LONG).show();
        }
    }

    private void setMainTitle() {
        TextView tvMain = findViewById(R.id.tv_main_title);

        if (mSortOrder == 0) {
            tvMain.setText(R.string.movies_by_popularity);
        } else if (mSortOrder == 1) {
            tvMain.setText(R.string.movies_by_top_rated);
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        return new MoviesLoader(this, mSortOrder);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        if (mSortOrder == 0) {
            sMovieByPopularity = movies;
        } else {
            sMovieByTopRated = movies;
        }

        constructMoviesGridView(movies);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {

    }

//    private class RetrieveMoviesAsyncTask extends AsyncTask<Void, Void, ArrayList<Movie>> {
//
//        private final String LOG_TAG = RetrieveMoviesAsyncTask.class.getSimpleName();
//
//        @Override
//        protected ArrayList<Movie> doInBackground(Void... voids) {
//            ArrayList<Movie> movies = new ArrayList<>();
//
//            try {
//                UriHelper uriHelper = new UriHelper();
//                String uriString = (mSortOrder == 0) ?
//                        uriHelper.getPopularMoviesUriString("77ea09a490e5a4a8bed60fbc1bf1c716") :
//                        uriHelper.getTopRatedMoviesUriString("77ea09a490e5a4a8bed60fbc1bf1c716");
//                URL url = new URL(uriString);
//
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//
//                try {
//                    //Read the input stream into buffer
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//                    StringBuilder responseBuilder = new StringBuilder();
//                    String responseLine;
//
//                    //Read the buffer line by line and append to responseBuilder
//                    while (null != (responseLine = bufferedReader.readLine())) {
//                        responseBuilder.append(responseLine).append("\n");
//                    }
//
//                    bufferedReader.close();
//
//                    JsonHelper jsonHelper = new JsonHelper();
//                    movies = jsonHelper.parseMoviesJson(responseBuilder.toString());
//                } finally {
//                    httpURLConnection.disconnect();
//                }
//
//            } catch (MalformedURLException e) {
//                Log.e(LOG_TAG, e.getMessage());
//            } catch (Exception e) {
//                Log.e(LOG_TAG, e.getMessage());
//            }
//
//            return movies;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Movie> movies) {
//            super.onPostExecute(movies);
//
//
//        }
//    }
}
