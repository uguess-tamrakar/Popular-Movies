package com.tamrakar.uguess.popularmovies.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.tamrakar.uguess.popularmovies.R;
import com.tamrakar.uguess.popularmovies.adapters.MainFragmentPagerAdapter;
import com.tamrakar.uguess.popularmovies.databinding.ActivityMainBinding;
import com.tamrakar.uguess.popularmovies.models.Movie;
import com.tamrakar.uguess.popularmovies.viewmodels.FavMoviesViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //region Variables...
    public static final int ADD_FAV_MOVIES_REQUEST_CODE = 1;
    private FavMoviesViewModel mFavMoviesViewModel;
    //endregion

    //region Overridden Methods...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavMoviesViewModel = ViewModelProviders.of(this).get(FavMoviesViewModel.class);

        ActivityMainBinding mainBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_main);

        setSupportActionBar(mainBinding.toolbar);

        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(this,
                getSupportFragmentManager());
        mainBinding.viewPagerMain.setAdapter(adapter);
        mainBinding.tabLayoutMain.setupWithViewPager(mainBinding.viewPagerMain);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_preferences) {
            startActivity(new Intent(this, PreferencesActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_FAV_MOVIES_REQUEST_CODE && resultCode == RESULT_OK) {
            Movie favMovie = data.getParcelableExtra(DetailActivity.ADD_FAV_MOVIE);
            mFavMoviesViewModel.insert(favMovie);
        }
    }

    //endregion

}
