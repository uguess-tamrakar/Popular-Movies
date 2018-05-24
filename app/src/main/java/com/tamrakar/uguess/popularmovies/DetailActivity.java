package com.tamrakar.uguess.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tamrakar.uguess.popularmovies.helpers.UriHelper;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_header);

        setHeaderInformation();
    }

    private void setHeaderInformation() {
        Intent intent = getIntent();
        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(intent.getStringExtra("title"));

        ImageView ivMovieBackdrop = findViewById(R.id.iv_movie_backdrop);

        try {
            String movieBackdropUri = new UriHelper().getTmdbMovieBackdropUriString(intent.getStringExtra("backdrop_path"));
            Picasso.with(this)
                    .load(movieBackdropUri)
                    .placeholder(R.drawable.ic_movie_info_24px)
                    .into(ivMovieBackdrop);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        TextView tvOriginalTitle = findViewById(R.id.tv_original_title);
        tvOriginalTitle.setText(intent.getStringExtra("original_title"));

        TextView tvOverview = findViewById(R.id.tv_overview);
        tvOverview.setText(intent.getStringExtra("overview"));

        String userRating = intent.getStringExtra("user_rating");
        TextView tvUserRating = findViewById(R.id.tv_user_rating);
        tvUserRating.setText(userRating + "/10");

        RatingBar rbUserRating = findViewById(R.id.rb_user_rating);
        rbUserRating.setRating(Float.valueOf(userRating) / 2);

        String releasedDate = intent.getStringExtra("release_date");
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
        TextView tvReleasedOn = findViewById(R.id.tv_released_on);
        tvReleasedOn.setText(formatter.format(java.sql.Date.valueOf(releasedDate)));
    }
}
