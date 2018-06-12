package com.tamrakar.uguess.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tamrakar.uguess.popularmovies.R;
import com.tamrakar.uguess.popularmovies.models.MovieTrailer;

import java.util.List;

public class MovieTrailersAdapter extends RecyclerView.Adapter<MovieTrailersAdapter.ViewHolder> {

    //region Variables...
    private static final String LOG_TAG = MovieTrailersAdapter.class.getSimpleName();
    private List<MovieTrailer> mMovieTrailers;
    //endregion

    //region Constructors...
    public MovieTrailersAdapter(List<MovieTrailer> movieTrailers) {
        mMovieTrailers = movieTrailers;
    }
    //endregion

    //region Overridden Methods...
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_detail_footer_trailer, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextViewTrailerTitle.setText(mMovieTrailers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mMovieTrailers.size();
    }
    //endregion

    //region ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewTrailerTitle;

        public ViewHolder(View view) {
            super(view);
            mTextViewTrailerTitle = view.findViewById(R.id.tv_trailer_title);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO launch trailer on YouTube
                }
            });
        }
    }
    //endregion
}
