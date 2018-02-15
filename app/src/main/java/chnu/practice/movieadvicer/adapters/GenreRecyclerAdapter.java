package chnu.practice.movieadvicer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import chnu.practice.movieadvicer.R;
import chnu.practice.movieadvicer.models.GenreModel.Genre;
import chnu.practice.movieadvicer.models.GenreModel.Genres;


public class GenreRecyclerAdapter extends RecyclerView.Adapter<GenreRecyclerAdapter.GenreViewHolder> {

    private Genres genres;
    private Context context;

    public GenreRecyclerAdapter(Genres genres, Context context) {
        this.genres = genres;
        this.context = context;
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
                Toast.makeText(context, "Clicked on " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return genres.getGenres().size();
    }


    class GenreViewHolder extends RecyclerView.ViewHolder {

        TextView genreItem;

        public GenreViewHolder(View itemView) {
            super(itemView);
            genreItem = itemView.findViewById(R.id.genre_item_text);
        }
    }
}
