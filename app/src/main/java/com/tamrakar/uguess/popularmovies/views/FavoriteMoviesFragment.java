package com.tamrakar.uguess.popularmovies.views;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tamrakar.uguess.popularmovies.R;
import com.tamrakar.uguess.popularmovies.adapters.MoviesAdapter;
import com.tamrakar.uguess.popularmovies.databinding.FragmentMainGridBinding;
import com.tamrakar.uguess.popularmovies.models.Movie;
import com.tamrakar.uguess.popularmovies.models.Movies;
import com.tamrakar.uguess.popularmovies.viewmodels.FavMoviesViewModel;
import com.tamrakar.uguess.popularmovies.viewmodels.PopularMoviesViewModel;

import java.util.List;

public class FavoriteMoviesFragment extends Fragment {

    //region Variables...
    private Activity mContext;
    private FragmentMainGridBinding mBinding;
    //endregion

    //region Constructors...
    public FavoriteMoviesFragment() {
    }
    //endregion

    //region Overridden Methods...
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_grid,
                container, false);
        setupViewModel();
        return mBinding.getRoot();
    }

    private void setupViewModel() {
        final FavMoviesViewModel viewModel = ViewModelProviders
                .of(this)
                .get(FavMoviesViewModel.class);
        List<Integer> favMovieIds = viewModel.getmFavMovieIds().getValue();

        viewModel.getFavMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> favMovies) {
                if (favMovies != null) {
                    MoviesAdapter moviesAdapter = new MoviesAdapter(mContext, favMovies);
                    mBinding.gvMain.setAdapter(moviesAdapter);
                }
            }
        });

        mBinding.tvMainTitle.setText(R.string.my_favorites);
    }
    //endregion
}
