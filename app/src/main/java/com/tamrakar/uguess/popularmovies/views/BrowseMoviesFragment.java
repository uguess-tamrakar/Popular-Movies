package com.tamrakar.uguess.popularmovies.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tamrakar.uguess.popularmovies.R;
import com.tamrakar.uguess.popularmovies.adapters.MoviesAdapter;
import com.tamrakar.uguess.popularmovies.databinding.FragmentMainGridBinding;
import com.tamrakar.uguess.popularmovies.models.Movies;
import com.tamrakar.uguess.popularmovies.viewmodels.PopularMoviesViewModel;
import com.tamrakar.uguess.popularmovies.viewmodels.TopRatedMoviesViewModel;

public class BrowseMoviesFragment extends Fragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    //region Variables...
    private static final String GRID_VIEW_STATE = "grid_view_state";
    private static final String KEY_PREF_SORT_ORDER = "sort_order";
    private int mSortOrder = 0;
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private FragmentMainGridBinding mBinding;
    private static Parcelable sGridViewState;
    private static boolean sRefreshGridView;
    //endregion

    //region Constructors...
    public BrowseMoviesFragment() {
    }
    //endregion

    //region Overridden Methods...


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
        PreferenceManager.setDefaultValues(mContext, R.xml.preferences_main, false);
        mSortOrder = Integer.valueOf(mSharedPreferences.getString(KEY_PREF_SORT_ORDER, ""));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (sRefreshGridView) {
            sGridViewState = null;
            sRefreshGridView = false;
        } else {
            if (savedInstanceState != null) {
                sGridViewState = savedInstanceState.getParcelable(GRID_VIEW_STATE);
            }
        }

        setUpViewModelBasedOnSortOrder(sGridViewState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_grid,
                        container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case KEY_PREF_SORT_ORDER:
                mSortOrder = Integer.valueOf(sharedPreferences.getString(key, ""));
                sRefreshGridView = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (sRefreshGridView) {
            sGridViewState = null;
            sRefreshGridView = false;
            setUpViewModelBasedOnSortOrder(sGridViewState);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        sGridViewState = mBinding.gvMain.onSaveInstanceState();
        outState.putParcelable(GRID_VIEW_STATE, sGridViewState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
    //endregion

    //region Helper Methods...
    private void setUpViewModelBasedOnSortOrder(final Parcelable gridViewState) {
        if (mSortOrder == 0) {
            final PopularMoviesViewModel viewModel = ViewModelProviders
                    .of(this)
                    .get(PopularMoviesViewModel.class);

            viewModel.getObservableMovies().observe(this, new Observer<Movies>() {
                @Override
                public void onChanged(@Nullable Movies movies) {
                    if (movies != null) {
                        MoviesAdapter moviesAdapter = new MoviesAdapter(mContext, movies.getMovies());
                        mBinding.gvMain.setAdapter(moviesAdapter);

                        if (gridViewState != null) {
                            mBinding.gvMain.onRestoreInstanceState(gridViewState);
                        }
                    }
                }
            });

            mBinding.tvMainTitle.setText(R.string.movies_by_popularity);
        } else if (mSortOrder == 1) {
            final TopRatedMoviesViewModel viewModel = ViewModelProviders
                    .of(this)
                    .get(TopRatedMoviesViewModel.class);
            viewModel.getObservableMovies().observe(this, new Observer<Movies>() {
                @Override
                public void onChanged(@Nullable Movies movies) {
                    if (movies != null) {
                        MoviesAdapter moviesAdapter = new MoviesAdapter(mContext, movies.getMovies());
                        mBinding.gvMain.setAdapter(moviesAdapter);

                        if (gridViewState != null) {
                            mBinding.gvMain.onRestoreInstanceState(gridViewState);
                        }
                    }
                }
            });

            mBinding.tvMainTitle.setText(R.string.movies_by_top_rated);
        }
    }
    //endregion
}
