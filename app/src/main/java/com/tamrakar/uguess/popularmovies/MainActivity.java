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

import com.tamrakar.uguess.popularmovies.helpers.JsonHelper;
import com.tamrakar.uguess.popularmovies.helpers.UriHelper;
import com.tamrakar.uguess.popularmovies.model.Movie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String KEY_PREF_SORT_ORDER = "sort_order";
    private SharedPreferences sharedPreferences;

    //<editor-fold desc="Overridden Methods">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the list of movies in asynchronous task
        new RetrieveMoviesAsyncTask().execute();

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
        if (key == getResources().getString(R.string.key_pref_sort_order)) {
            setMainTitle();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    //</editor-fold>

    private void constructMoviesGridView(List<Movie> movies) {
        GridView gvMain = findViewById(R.id.gv_main);
        MoviesAdapter moviesAdapter = new MoviesAdapter(this, movies);
        gvMain.setAdapter(moviesAdapter);
    }

    private void setMainTitle() {
        TextView tvMain = findViewById(R.id.tv_main_title);

        int sortOrder = Integer.valueOf(sharedPreferences.getString(KEY_PREF_SORT_ORDER, ""));

        if (sortOrder == 0) {
            tvMain.setText(R.string.movies_by_popularity);
        } else if (sortOrder == 1) {
            tvMain.setText(R.string.movies_by_top_rated);
        }
    }

    private class RetrieveMoviesAsyncTask extends AsyncTask<Void, Void, List<Movie>> {

        private final String LOG_TAG = RetrieveMoviesAsyncTask.class.getSimpleName();

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            List<Movie> movies = new ArrayList<>();

            try {
                UriHelper uriHelper = new UriHelper();
                URL urlPopularMovies = new URL(uriHelper.getPopularMoviesUriString("77ea09a490e5a4a8bed60fbc1bf1c716"));

                HttpURLConnection httpURLConnection = (HttpURLConnection) urlPopularMovies.openConnection();

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

                    if (null != responseBuilder.toString()) {
                        JsonHelper jsonHelper = new JsonHelper();
                        movies = jsonHelper.parseMoviesJson(responseBuilder.toString());
                    }
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
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            constructMoviesGridView(movies);
        }
    }
}
