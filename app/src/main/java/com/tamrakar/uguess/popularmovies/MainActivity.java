package com.tamrakar.uguess.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.tamrakar.uguess.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the list of movies
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Test", "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "test", "4", "05/17/2018"));

        GridView gvMain = (GridView) findViewById(R.id.gv_main);
        MoviesAdapter moviesAdapter = new MoviesAdapter(this, movies);
        gvMain.setAdapter(moviesAdapter);
    }
}
