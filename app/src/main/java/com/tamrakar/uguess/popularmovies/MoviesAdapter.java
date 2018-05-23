package com.tamrakar.uguess.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tamrakar.uguess.popularmovies.helpers.UriHelper;
import com.tamrakar.uguess.popularmovies.model.Movie;

import java.util.List;

public class MoviesAdapter extends ArrayAdapter<Movie> {

    private static final String LOG_TAG = MoviesAdapter.class.getSimpleName();
    private Context mContext;

    public MoviesAdapter(@NonNull Context context, @NonNull List<Movie> movies) {
        super(context, 0, movies);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Get the movie at this position
        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_main, parent, false);

            ImageView ivMoviePoster = convertView.findViewById(R.id.iv_movie_poster);
            ivMoviePoster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(getContext(), DetailActivity.class));
                }
            });

            try {
                String moviePosterUri = new UriHelper().getTmdbImageUriString(movie.getPosterImagePath());
                Picasso.with(getContext())
                        .load(moviePosterUri)
                        .into(ivMoviePoster);
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }

        return convertView;
    }
}
