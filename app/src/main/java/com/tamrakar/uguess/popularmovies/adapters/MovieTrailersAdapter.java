package com.tamrakar.uguess.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tamrakar.uguess.popularmovies.R;
import com.tamrakar.uguess.popularmovies.helpers.UriHelper;
import com.tamrakar.uguess.popularmovies.models.MovieTrailer;

import java.util.List;

public class MovieTrailersAdapter extends RecyclerView.Adapter<MovieTrailersAdapter.ViewHolder> {

    //region Variables...
    private static final String LOG_TAG = MovieTrailersAdapter.class.getSimpleName();
    private List<MovieTrailer> mMovieTrailers;
    private Context mContext;
    //endregion

    //region Constructors...
    public MovieTrailersAdapter(Context context, List<MovieTrailer> movieTrailers) {
        mMovieTrailers = movieTrailers;
        mContext = context;
    }
    //endregion

    //region Overridden Methods...
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_detail_footer_trailer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MovieTrailer movieTrailer = mMovieTrailers.get(position);
        holder.mTextViewTrailerTitle.setText(movieTrailer.getName());

        if (movieTrailer.isYouTubeTrailer()) {
            try {
                Picasso.with(mContext)
                        .load(UriHelper.getYoutTubeThumbnailUriString(movieTrailer.getKey()))
                        .placeholder(new ColorDrawable(mContext.getResources().getColor(R.color.colorPrimaryUltraDark)))
                        .into(holder.mImageViewTrailerThumbnail);
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(UriHelper.getYoutTubeVideoUriString(movieTrailer.getKey())));
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieTrailers.size();
    }
    //endregion

    //region ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewTrailerTitle;
        ImageView mImageViewTrailerThumbnail;

        ViewHolder(View view) {
            super(view);
            mTextViewTrailerTitle = view.findViewById(R.id.tv_trailer_title);
            mImageViewTrailerThumbnail = view.findViewById(R.id.iv_movie_trailer_thumbnail);
        }
    }
    //endregion
}
