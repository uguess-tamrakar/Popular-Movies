package com.tamrakar.uguess.popularmovies.views;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.AttrRes;
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
import com.tamrakar.uguess.popularmovies.adapters.MovieReviewsAdapter;
import com.tamrakar.uguess.popularmovies.databinding.FragmentDetailFooterReviewsBinding;
import com.tamrakar.uguess.popularmovies.helpers.MovieReviewsLoader;
import com.tamrakar.uguess.popularmovies.models.Movie;
import com.tamrakar.uguess.popularmovies.models.MovieReview;

import java.util.ArrayList;

public class MovieReviewsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList<MovieReview>> {

    private static final String CURRENT_MOVIE = "current_movie";
    private Movie mMovie;
    private FragmentDetailFooterReviewsBinding mBinding;

    public MovieReviewsFragment() {

    }

    public static MovieReviewsFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_MOVIE, movie);

        MovieReviewsFragment fragment = new MovieReviewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_footer_reviews,
                container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMovie = getArguments().getParcelable(CURRENT_MOVIE);

        mBinding.rvMovieReviews.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.rvMovieReviews.setLayoutManager(layoutManager);

        //TODO: Remove the mysterious control when scrolling RecyclerView upwards
        //While scrolling the Movie Reviews RecyclerView up, I see a control at the bottom
        //Couldn't find a solution on how to make this now show up.

        getLoaderManager().initLoader(MovieReviewsLoader.MOVIE_REVIEWS_LOADER_ID,
                null, this);
    }

    @Override
    public Loader<ArrayList<MovieReview>> onCreateLoader(int id, @Nullable Bundle args) {
        return new MovieReviewsLoader(getContext(), String.valueOf(mMovie.getMovieId()));
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieReview>> loader, ArrayList<MovieReview> data) {
        MovieReviewsAdapter movieReviewsAdapter = new MovieReviewsAdapter(data);
        mBinding.rvMovieReviews.setAdapter(movieReviewsAdapter);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
