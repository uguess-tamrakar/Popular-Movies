package com.tamrakar.uguess.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tamrakar.uguess.popularmovies.R;
import com.tamrakar.uguess.popularmovies.models.Movie;
import com.tamrakar.uguess.popularmovies.views.MovieDetailsFragment;
import com.tamrakar.uguess.popularmovies.views.MovieReviewsFragment;

public class DetailFragmentPagerAdapter extends FragmentPagerAdapter {

    //region Variables...
    private Context mContext;
    private Movie mMovie;
    //endregion

    //region Constructors...
    public DetailFragmentPagerAdapter(Context context, FragmentManager fm, Movie movie) {
        super(fm);
        mContext = context;
        mMovie = movie;
    }
    //endregion

    //region Overridden Methods...
    @Override
    public Fragment getItem(int position) {
        Fragment resultFragment = null;
        switch (position) {
            case 0:
                resultFragment = MovieDetailsFragment.newInstance(mMovie);
                break;
            case 1:
                resultFragment = MovieReviewsFragment.newInstance(mMovie);
                break;
        }

        //TODO: In activity_detail_footer, make viewpager takes the remaining height with scroll
        //The viewpager do not show if I set the layout_height to wrap_content or match_parent.
        //I have to hard code it for some reason. Also tried setting layout_weight but didn't help.

        return resultFragment;
    }

    @Override
    public int getCount() {
        //Details and Reviews tab
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.tab_item_details_text);
            case 1:
                return mContext.getString(R.string.tab_item_reviews_text);
            default:
                return null;
        }
    }
    //endregion

}
