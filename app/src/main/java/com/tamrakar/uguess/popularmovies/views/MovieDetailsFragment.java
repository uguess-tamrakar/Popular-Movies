package com.tamrakar.uguess.popularmovies.views;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tamrakar.uguess.popularmovies.R;
import com.tamrakar.uguess.popularmovies.adapters.MovieTrailersAdapter;
import com.tamrakar.uguess.popularmovies.databinding.FragmentDetailFooterDetailsBinding;
import com.tamrakar.uguess.popularmovies.helpers.MovieTrailersLoader;
import com.tamrakar.uguess.popularmovies.models.Movie;
import com.tamrakar.uguess.popularmovies.models.MovieTrailer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MovieDetailsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList<MovieTrailer>> {

    //region Variables...
    private static final String CURRENT_MOVIE = "current_movie";
    private FragmentDetailFooterDetailsBinding mBinding;
    private Movie mMovie;
    //endregion

    //region Constructors...
    public MovieDetailsFragment() {
    }

    public static MovieDetailsFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_MOVIE, movie);

        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

    //region Overridden Methods...
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(CURRENT_MOVIE);
            SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);

            mBinding.rvMovieTrailers.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mBinding.rvMovieTrailers.setLayoutManager(layoutManager);

            mBinding.tvOriginalTitle.setText(mMovie.getOriginalTitle());
            mBinding.tvOverview.setText(mMovie.getOverview());
            mBinding.tvReleasedOn.setText(formatter
                    .format(java.sql.Date.valueOf(mMovie.getReleaseDate())));
        }

        //TODO Set fab button image according to if the movie has already been favored
        // Wanting to setup image on the fab button to different images based on if its already
        // in the user favorites list but couldn't figure out how to check that.

        getLoaderManager().initLoader(MovieTrailersLoader.MOVIE_TRAILERS_LOADER_ID, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_footer_details,
                        container, false);

        return mBinding.getRoot();
    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieTrailer>> onCreateLoader(int id, @Nullable Bundle args) {
        return new MovieTrailersLoader(getContext(), String.valueOf(mMovie.getMovieId()));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieTrailer>> loader, ArrayList<MovieTrailer> data) {
        MovieTrailersAdapter movieTrailersAdapter = new MovieTrailersAdapter(getContext(), data);
        mBinding.rvMovieTrailers.setAdapter(movieTrailersAdapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieTrailer>> loader) {

    }
    //endregion
}
