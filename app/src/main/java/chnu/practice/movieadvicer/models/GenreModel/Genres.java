package chnu.practice.movieadvicer.models.GenreModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Genres {

    public Genres(List<Genre> genres) {
        this.genres = genres;
    }

    @SerializedName("genres")
    private List<Genre> genres = null;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

}