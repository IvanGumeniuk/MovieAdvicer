package chnu.practice.movieadvicer.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import chnu.practice.movieadvicer.R;
import chnu.practice.movieadvicer.models.GenreModel.Genre;
import chnu.practice.movieadvicer.models.GenreModel.Genres;


public class GenreRecyclerAdapter extends RecyclerView.Adapter<GenreRecyclerAdapter.GenreViewHolder> {

    private Genres genres;

    public interface GenreRecyclerAdapterInterface {
        void onGenreClick(Genre genre);
    }

    GenreRecyclerAdapterInterface mListener;

    public GenreRecyclerAdapter(GenreRecyclerAdapterInterface adapterInterface) {
        mListener = adapterInterface;
    }

    public void setData(Genres genres){
        this.genres = genres;
        notifyDataSetChanged();
    }

    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_genre, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, int position) {
        final Genre item = genres.getGenres().get(position);
        holder.genreItem.setText(item.getName());
        holder.genreItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null){
                    mListener.onGenreClick(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return genres != null && genres.getGenres() != null ? genres.getGenres().size() : 0;
    }


    class GenreViewHolder extends RecyclerView.ViewHolder {

        TextView genreItem;

        public GenreViewHolder(View itemView) {
            super(itemView);
            genreItem = itemView.findViewById(R.id.genre_item_text);
        }
    }
}
