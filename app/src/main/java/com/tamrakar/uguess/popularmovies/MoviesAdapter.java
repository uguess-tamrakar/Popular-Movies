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
import com.tamrakar.uguess.popularmovies.models.Movie;

import java.util.List;

public class MoviesAdapter extends ArrayAdapter<Movie> {

    //region Variables...
    private static final String LOG_TAG = MoviesAdapter.class.getSimpleName();
    private Context mContext;
    //endregion

    //region Constructors...
    public MoviesAdapter(@NonNull Context context, @NonNull List<Movie> movies) {
        super(context, 0, movies);
        mContext = context;
    }
    //endregion

    //region Overridden Methods...
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Get the movie at this position
        final Movie movie = getItem(position);
        final Context context = getContext();

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.fragment_main, parent, false);
        }

        final ImageView ivMoviePoster = convertView.findViewById(R.id.iv_movie_poster);
        ivMoviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("current_movie", movie);
                mContext.startActivity(intent);
            }
        });

        try {
            if (movie != null) {
                String moviePosterUri = new UriHelper().getTmdbMoviePosterUriString(movie.getPosterImagePath());
                Picasso.with(getContext())
                        .load(moviePosterUri)
                        .placeholder(R.drawable.ic_movie_info_24px)
                        .into(ivMoviePoster);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        return convertView;
    }
    //endregion

}
