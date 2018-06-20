package com.tamrakar.uguess.popularmovies.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tamrakar.uguess.popularmovies.R;
import com.tamrakar.uguess.popularmovies.adapters.DetailFragmentPagerAdapter;
import com.tamrakar.uguess.popularmovies.adapters.MoviesAdapter;
import com.tamrakar.uguess.popularmovies.databinding.ActivityDetailFooterBinding;
import com.tamrakar.uguess.popularmovies.databinding.ActivityDetailHeaderBinding;
import com.tamrakar.uguess.popularmovies.helpers.UriHelper;
import com.tamrakar.uguess.popularmovies.models.Movie;
import com.tamrakar.uguess.popularmovies.viewmodels.FavMoviesViewModel;

public class DetailActivity extends AppCompatActivity {

    //region Variables...
    private final String LOG_TAG = DetailActivity.class.getSimpleName();
    public static final String ADD_FAV_MOVIE = "AddFavMovie";
    public static final String DELETE_FAV_MOVIE = "DeleteFavMovie";
    //endregion

    //region Overridden Methods...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHeaderInformation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
    //endregion

    //region Helper Methods...
    private void setHeaderInformation() {
        Intent intent = getIntent();
        final Movie currentMovie = intent.getParcelableExtra(MoviesAdapter.CLICKED_MOVIE);

        ActivityDetailHeaderBinding headerBinding = DataBindingUtil.
                setContentView(this, R.layout.activity_detail_header);

        setSupportActionBar(headerBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        headerBinding.collapsingToolbar.setTitle(currentMovie.getTitle());

        try {
            String movieBackdropUri = UriHelper
                    .getTmdbMovieBackdropUriString(currentMovie.getBackdropPath());
            Picasso.with(this)
                    .load(movieBackdropUri)
                    .placeholder(R.drawable.ic_movie_info_24px)
                    .into(headerBinding.ivMovieBackdrop);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        ActivityDetailFooterBinding footerBinding = DataBindingUtil.
                bind(findViewById(R.id.footer));
        DetailFragmentPagerAdapter adapter = new DetailFragmentPagerAdapter(this,
                getSupportFragmentManager(), currentMovie);
        footerBinding.viewPagerDetail.setAdapter(adapter);
        footerBinding.tabLayoutDetail.setupWithViewPager(footerBinding.viewPagerDetail);

        String userRating = currentMovie.getUserRating();
        footerBinding.tvUserRating.setText(String.format("%s/10", userRating));
        footerBinding.rbUserRating.setRating(Float.valueOf(userRating) / 2);

        FavMoviesViewModel viewModel = ViewModelProviders
                .of(this)
                .get(FavMoviesViewModel.class);
        final boolean isFavoriteMovie = viewModel.doesContainFavMovie(currentMovie.getMovieId());

        if (isFavoriteMovie) {
            footerBinding.fabFavorite.setImageResource(R.drawable.ic_favorite_24px);
        } else {
            footerBinding.fabFavorite.setImageResource(R.drawable.ic_not_favorite_24px);
        }

        footerBinding.fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFavoritesButtonClicked(currentMovie, isFavoriteMovie);
            }
        });
    }

    private void onFavoritesButtonClicked(Movie movie, boolean alreadyExists) {
        Intent intent = new Intent();
        String message;

        if (alreadyExists) {
            intent.putExtra(DELETE_FAV_MOVIE, movie);
            message = movie.getTitle() + " has been removed from your favorites.";
        } else {
            intent.putExtra(ADD_FAV_MOVIE, movie);
            message = movie.getTitle() + " has been added to your favorites.";
        }

        setResult(RESULT_OK, intent);
        finish();

        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }
    //endregion
}
