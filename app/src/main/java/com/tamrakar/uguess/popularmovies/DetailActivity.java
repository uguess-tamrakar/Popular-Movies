package com.tamrakar.uguess.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tamrakar.uguess.popularmovies.data.FavMovieEntity;
import com.tamrakar.uguess.popularmovies.data.PopularMoviesDatabase;
import com.tamrakar.uguess.popularmovies.databinding.ActivityDetailFooterBinding;
import com.tamrakar.uguess.popularmovies.databinding.ActivityDetailHeaderBinding;
import com.tamrakar.uguess.popularmovies.helpers.UriHelper;
import com.tamrakar.uguess.popularmovies.models.Movie;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    //region Variables...
    private final String LOG_TAG = DetailActivity.class.getSimpleName();
    private PopularMoviesDatabase mDb;
    //endregion

    //region Overridden Methods...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_header);
        mDb = PopularMoviesDatabase.getInstance(getApplicationContext());
        setHeaderInformation();
    }
    //endregion

    //region Helper Methods...
    private void setHeaderInformation() {
        Intent intent = getIntent();
        final Movie currentMovie = intent.getParcelableExtra("current_movie");

        ActivityDetailHeaderBinding headerBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_detail_header);

        setSupportActionBar(headerBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        headerBinding.collapsingToolbar.setTitle(currentMovie.getTitle());

        try {
            String movieBackdropUri = new UriHelper()
                    .getTmdbMovieBackdropUriString(currentMovie.getBackdropPath());
            Picasso.with(this)
                    .load(movieBackdropUri)
                    .placeholder(R.drawable.ic_movie_info_24px)
                    .into(headerBinding.ivMovieBackdrop);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        ActivityDetailFooterBinding footerBinding = DataBindingUtil.bind(findViewById(R.id.footer));
        String userRating = currentMovie.getUserRating();
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);

        footerBinding.tvOriginalTitle.setText(currentMovie.getOriginalTitle());
        footerBinding.tvOverview.setText(currentMovie.getOverview());
        footerBinding.tvUserRating.setText(userRating + "/10");
        footerBinding.rbUserRating.setRating(Float.valueOf(userRating) / 2);
        footerBinding.tvReleasedOn.setText(formatter
                .format(java.sql.Date.valueOf(currentMovie.getReleaseDate())));

        footerBinding.ibFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddToFavoritesButtonClicked(currentMovie.getTitle());
            }
        });
    }
    //endregion

    private void onAddToFavoritesButtonClicked(String title) {
        FavMovieEntity favMovieEntity = new FavMovieEntity(title);
        mDb.favMovieDao().insertFavMovie(favMovieEntity);
        finish();

        Toast.makeText(getApplicationContext(),
                title + " has been added to your favorites", Toast.LENGTH_LONG).show();
    }
}
