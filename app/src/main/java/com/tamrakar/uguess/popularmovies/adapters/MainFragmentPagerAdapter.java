package com.tamrakar.uguess.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tamrakar.uguess.popularmovies.R;
import com.tamrakar.uguess.popularmovies.views.BrowseMoviesFragment;
import com.tamrakar.uguess.popularmovies.views.FavoriteMoviesFragment;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    //region Variables...
    private Context mContext;
    //endregion

    //region Constructors...
    public MainFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    //endregion

    //region Overridden Methods...
    @Override
    public Fragment getItem(int position) {
        Fragment resultFragment = null;
        switch (position) {
            case 0:
                resultFragment = new BrowseMoviesFragment();
                break;
            case 1:
                resultFragment = new FavoriteMoviesFragment();
                break;
        }

        return resultFragment;
    }

    @Override
    public int getCount() {
        //Browse and Favorites tab
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.tab_item_browse_text);
            case 1:
                return mContext.getString(R.string.tab_item_favorites_text);
            default:
                return null;
        }
    }
    //endregion

}
