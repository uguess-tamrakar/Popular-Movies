package com.tamrakar.uguess.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.tamrakar.uguess.popularmovies.helpers.JsonHelper;
import com.tamrakar.uguess.popularmovies.helpers.NetworkHelper;
import com.tamrakar.uguess.popularmovies.helpers.UriHelper;
import com.tamrakar.uguess.popularmovies.model.Movie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String KEY_PREF_SORT_ORDER = "sort_order";
    private static int sortOrder = 0;
    private SharedPreferences sharedPreferences;
    private static ArrayList<Movie> moviesByPopularity;
    private static ArrayList<Movie> moviesByTopRated;

    //<editor-fold desc="Overridden Methods">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean needToRetrieveMovies = false;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            if (sortOrder == 0) {
                if (moviesByPopularity == null || moviesByPopularity.isEmpty()) {
                    moviesByPopularity = savedInstanceState.getParcelableArrayList("movies_by_popularity");

                    if (moviesByPopularity == null) {
                        needToRetrieveMovies = true;
                    } else {
                        constructMoviesGridView(moviesByPopularity);
                    }
                }
            } else if (sortOrder == 1) {
                if (moviesByTopRated == null || moviesByTopRated.isEmpty()) {
                    moviesByTopRated = savedInstanceState.getParcelableArrayList("movies_by_top_rated");

                    if (moviesByTopRated == null) {
                        needToRetrieveMovies = true;
                    } else {
                        constructMoviesGridView(moviesByTopRated);
                    }
                }
            }

        } else {
            if (sortOrder == 0) {
                if (moviesByPopularity == null || moviesByPopularity.isEmpty()) {
                    needToRetrieveMovies = true;
                } else {
                    constructMoviesGridView(moviesByPopularity);
                }
            } else if (sortOrder == 1) {
                if (moviesByTopRated == null || moviesByTopRated.isEmpty()) {
                    needToRetrieveMovies = true;
                } else {
                    constructMoviesGridView(moviesByTopRated);
                }
            }
        }

        if (needToRetrieveMovies) {

            if (NetworkHelper.isConnectedToInternet(this)) {
                //Get the list of movies in asynchronous task
                new RetrieveMoviesAsyncTask().execute();
            } else {
                Toast.makeText(this, "Not connected to internet.", Toast.LENGTH_LONG).show();
            }
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        PreferenceManager.setDefaultValues(this, R.xml.preferences_main, false);
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
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies_by_popularity", moviesByPopularity);
        outState.putParcelableArrayList("movies_by_top_rated", moviesByTopRated);
        super.onSaveInstanceState(outState);
    }

    //</editor-fold>

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

        sortOrder = Integer.valueOf(sharedPreferences.getString(KEY_PREF_SORT_ORDER, ""));

        if (sortOrder == 0) {
            tvMain.setText(R.string.movies_by_popularity);
        } else if (sortOrder == 1) {
            tvMain.setText(R.string.movies_by_top_rated);
        }
    }

    private class RetrieveMoviesAsyncTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

        private final String LOG_TAG = RetrieveMoviesAsyncTask.class.getSimpleName();

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            ArrayList<Movie> movies = new ArrayList<>();

            try {
                UriHelper uriHelper = new UriHelper();
                String uriString = (sortOrder == 0) ?
                        uriHelper.getPopularMoviesUriString("77ea09a490e5a4a8bed60fbc1bf1c716") :
                        uriHelper.getTopRatedMoviesUriString("77ea09a490e5a4a8bed60fbc1bf1c716");
                URL url = new URL(uriString);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                try {
                    //Read the input stream into buffer
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder responseBuilder = new StringBuilder();
                    String responseLine;

                    //Read the buffer line by line and append to responseBuilder
                    while (null != (responseLine = bufferedReader.readLine())) {
                        responseBuilder.append(responseLine).append("\n");
                    }

                    bufferedReader.close();

                    JsonHelper jsonHelper = new JsonHelper();
                    movies = jsonHelper.parseMoviesJson(responseBuilder.toString());
                } finally {
                    httpURLConnection.disconnect();
                }

            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, e.getMessage());
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            }

            return movies;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);

            if (sortOrder == 0) {
                moviesByPopularity = movies;
            } else {
                moviesByTopRated = movies;
            }

            constructMoviesGridView(movies);
        }
    }
}
