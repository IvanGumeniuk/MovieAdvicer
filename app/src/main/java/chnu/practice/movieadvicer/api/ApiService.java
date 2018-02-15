package chnu.practice.movieadvicer.api;

import chnu.practice.movieadvicer.models.GenreModel.Genres;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ваня on 15.02.2018.
 */

public interface ApiService {
    @GET("genre/movie/list")
    Call<Genres> getGenres(@Query("api_key") String key, @Query("language") String language );
}
