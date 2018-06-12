package com.tamrakar.uguess.popularmovies.views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class DetailActivity extends AppCompatActivity {

    //region Variables...
    private final String LOG_TAG = DetailActivity.class.getSimpleName();
    public static final String ADD_FAV_MOVIE = "AddFavMovie";
    private ShareActionProvider mShareActionProvider;
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
        MenuItem item = menu.findItem(R.id.share_movie_trailer);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        return true;
    }
    //endregion

    //region Helper Methods...
    private void setHeaderInformation() {
        Intent intent = getIntent();
        final Movie currentMovie = intent.getParcelableExtra(MoviesAdapter.CLICKED_MOVIE);

        ActivityDetailHeaderBinding headerBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_detail_header);

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
        footerBinding.tvUserRating.setText(userRating + "/10");
        footerBinding.rbUserRating.setRating(Float.valueOf(userRating) / 2);

        footerBinding.fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddToFavoritesButtonClicked(currentMovie);
            }
        });
    }
    //endregion

    private void onAddToFavoritesButtonClicked(Movie movie) {
        Intent intent = new Intent();
        intent.putExtra(ADD_FAV_MOVIE, movie);
        setResult(RESULT_OK, intent);
        finish();

        Toast.makeText(getApplicationContext(),
                movie.getTitle() + " has been added to your favorites.",
                Toast.LENGTH_LONG).show();
    }
}
