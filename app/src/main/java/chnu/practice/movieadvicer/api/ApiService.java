package chnu.practice.movieadvicer.api;

import chnu.practice.movieadvicer.models.GenreModel.Genres;
import chnu.practice.movieadvicer.models.MovieModel.Movies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ваня on 15.02.2018.
 */

public interface ApiService {
    String API_KEY = "api_key";
    String LANGUAGE = "language";

    @GET("genre/movie/list")
    Call<Genres> getGenres(@Query(API_KEY) String key, @Query(LANGUAGE) String language);

    @GET("genre/{genreId}/movies")
    Call<Movies> getMoviesByGenre(@Path("genreId") int genreId, @Query(API_KEY) String key,
                                  @Query(LANGUAGE) String language,
                                  @Query("include_adult") boolean needAdult,
                                  @Query("sort_by") String sortBy);

    @GET("genre/{genreId}/movies")
    Call<Movies> getMoviesByGenreWithPage(@Path("genreId") int genreId, @Query(API_KEY) String key,
                                  @Query(LANGUAGE) String language,
                                  @Query("include_adult") boolean needAdult,
                                  @Query("sort_by") String sortBy,
                                  @Query("page") int page);

}
