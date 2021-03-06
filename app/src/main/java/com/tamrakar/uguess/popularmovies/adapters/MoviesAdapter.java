package com.tamrakar.uguess.popularmovies.adapters;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.squareup.picasso.Picasso;
import com.tamrakar.uguess.popularmovies.R;
import com.tamrakar.uguess.popularmovies.databinding.FragmentMainGridItemBinding;
import com.tamrakar.uguess.popularmovies.helpers.UriHelper;
import com.tamrakar.uguess.popularmovies.models.Movie;
import com.tamrakar.uguess.popularmovies.viewmodels.FavMoviesViewModel;
import com.tamrakar.uguess.popularmovies.views.DetailActivity;
import com.tamrakar.uguess.popularmovies.views.MainActivity;

import java.util.List;

public class MoviesAdapter extends ArrayAdapter<Movie> {

    //region Variables...

    public static final String CLICKED_MOVIE = "clicked_movie";
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

        if (convertView == null) {
            convertView = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.fragment_main_grid_item, parent, false);
        }

        FragmentMainGridItemBinding binding = DataBindingUtil.bind(convertView);

        if (binding != null) {
            binding.tvMovieTitle.setText(movie != null ? movie.getTitle() : "Title not found");

            binding.ivMoviePoster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra(CLICKED_MOVIE, movie);

                    FavMoviesViewModel viewModel = ViewModelProviders
                            .of((FragmentActivity) mContext)
                            .get(FavMoviesViewModel.class);
                    boolean isFavoriteMovie = viewModel.doesContainFavMovie(movie.getMovieId());

                    int requestCode;

                    if (isFavoriteMovie) {
                        requestCode = MainActivity.REMOVE_FAV_MOVIES_REQUEST_CODE;
                    } else {
                        requestCode = MainActivity.ADD_FAV_MOVIES_REQUEST_CODE;
                    }

                    ((Activity) mContext).startActivityForResult(intent, requestCode);
                }
            });

            try {
                if (movie != null) {
                    String moviePosterUri = UriHelper
                            .getTmdbMoviePosterUriString(movie.getPosterImagePath());

                    Picasso.with(getContext())
                            .load(moviePosterUri)
                            .placeholder(new ColorDrawable(mContext.getResources().getColor(R.color.colorPrimaryUltraDark)))
                            .into(binding.ivMoviePoster);
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }

        return convertView;
    }
    //endregion

}
