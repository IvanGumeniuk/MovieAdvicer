package chnu.practice.movieadvicer.ui.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import chnu.practice.movieadvicer.R;
import chnu.practice.movieadvicer.consts.Constants;
import chnu.practice.movieadvicer.models.MovieModel.Movies;
import chnu.practice.movieadvicer.models.MovieModel.Result;


public class MoviesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private Movies mMovies;
    private MoviesItemClickListener mItemClickListener;

    public MoviesRecyclerAdapter(MoviesItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void setData(Movies movies) {
        this.mMovies = movies;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_movie, parent, false);
                holder = new MoviesViewHolder(view);
                break;
            case TYPE_FOOTER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_footer, parent, false);
                holder = new FooterViewHolder(view);
                break;
            default:
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MoviesViewHolder) {
            final Result item = mMovies.results.get(position);
            final MoviesViewHolder moviesViewHolder = (MoviesViewHolder) holder;
            moviesViewHolder.mMovieCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnMovieClick(item);
                    }
                }
            });
            moviesViewHolder.mMovieName.setText(item.title.length() > 30 ? item.title.substring(0, 27) + "..." : item.title);
            moviesViewHolder.mMovieDescription.setText(String.valueOf(item.releaseDate));
            moviesViewHolder.mMovieVote.setText(String.valueOf(item.voteAverage));
            moviesViewHolder.mMovieTotalVotes.setText(String.valueOf(item.voteCount));
            Picasso.with(moviesViewHolder.mMoviePoster.getContext())
                    .load(Constants.POSTER_BASE_URL + item.posterPath)
                    .error(R.drawable.ic_error)
                    .placeholder(R.drawable.picasso_placeholder)
                    .resize(200, 250)
                    .into(moviesViewHolder.mMoviePoster);

            moviesViewHolder.mFavoriteImage.setImageResource(item.isFavorite() ? R.drawable.ic_like
                    : R.drawable.ic_not_like);

            moviesViewHolder.mFavoriteImage.setTag(item.isFavorite() ? Constants.IS_FAVORITE
                    : Constants.IS_NOT_FAVORITE);

            moviesViewHolder.mFavoriteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int imageResource = R.drawable.ic_not_like;
                    int imageTag = Constants.IS_NOT_FAVORITE;
                    if((int)moviesViewHolder.mFavoriteImage.getTag() == Constants.IS_NOT_FAVORITE){
                        imageResource = R.drawable.ic_like;
                        imageTag = Constants.IS_FAVORITE;
                    }
                    moviesViewHolder.mFavoriteImage.setTag(imageTag);
                    moviesViewHolder.mFavoriteImage.setImageResource(imageResource);
                    if(mItemClickListener != null){
                        mItemClickListener.OnFavoriteImageClick(item);
                    }
                }
            });

        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.mFooterText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnShowMoreClick(mMovies.page);
                    }
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.results.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        int type = TYPE_ITEM;
        if (position == mMovies.results.size())
            type = TYPE_FOOTER;
        return type;
    }

    public interface MoviesItemClickListener {
        void OnMovieClick(Result result);

        void OnShowMoreClick(int page);

        void OnFavoriteImageClick(Result result);
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView mFooterText;

        public FooterViewHolder(View view) {
            super(view);
            mFooterText = view.findViewById(R.id.footerText);
        }
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        private CardView mMovieCardView;
        private TextView mMovieName;
        private TextView mMovieDescription;
        private ImageView mMoviePoster;
        private TextView mMovieVote;
        private TextView mMovieTotalVotes;
        private ImageView mFavoriteImage;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            mFavoriteImage = itemView.findViewById(R.id.imageFavorite);
            mMovieCardView = itemView.findViewById(R.id.movieCardView);
            mMovieName = itemView.findViewById(R.id.movieName);
            mMovieDescription = itemView.findViewById(R.id.movieDescription);
            mMoviePoster = itemView.findViewById(R.id.moviePoster);
            mMovieVote = itemView.findViewById(R.id.movieVote);
            mMovieTotalVotes = itemView.findViewById(R.id.movieTotalVotes);
        }
    }
}
