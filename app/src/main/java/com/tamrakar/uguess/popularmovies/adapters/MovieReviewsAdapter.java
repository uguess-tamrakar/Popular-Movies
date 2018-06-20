package com.tamrakar.uguess.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tamrakar.uguess.popularmovies.R;
import com.tamrakar.uguess.popularmovies.models.MovieReview;

import java.util.List;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ViewHolder> {

    private static final String LOG_TAG = MovieReviewsAdapter.class.getSimpleName();
    private List<MovieReview> mMovieReviews;

    public MovieReviewsAdapter(List<MovieReview> movieReviews) {
        this.mMovieReviews = movieReviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_detail_footer_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieReview movieReview = mMovieReviews.get(position);
        holder.mTextViewReviewContent.setText(movieReview.getContent());
        holder.mTextViewReviewAuthor.setText(movieReview.getAuthor());
    }

    @Override
    public int getItemCount() {
        return mMovieReviews.size();
    }

    //region ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewReviewAuthor;
        public TextView mTextViewReviewContent;

        public ViewHolder(View view) {
            super(view);
            mTextViewReviewAuthor = view.findViewById(R.id.tv_movie_review_author);
            mTextViewReviewContent = view.findViewById(R.id.tv_movie_review_content);
        }
    }
    //endregion
}
