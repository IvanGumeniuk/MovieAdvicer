package chnu.practice.movieadvicer.app;

import chnu.practice.movieadvicer.DefaultCallback;
import chnu.practice.movieadvicer.api.ApiService;
import chnu.practice.movieadvicer.consts.Constants;
import chnu.practice.movieadvicer.models.GenreModel.Genres;
import chnu.practice.movieadvicer.models.MovieModel.Movies;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ваня on 20.02.2018.
 */

public class RetrofitBase {
    private static RetrofitBase sInstance = new RetrofitBase();
    private Retrofit retrofit;
    private ApiService mApiService;

    private RetrofitBase(){
        retrofitInitialization();
    }

    public static RetrofitBase getInstance(){
        return sInstance;
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }

    private void retrofitInitialization(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    public void genresRequest(DefaultCallback<Genres> callback){
        mApiService.getGenres(Constants.API_KEY, Constants.LANGUAGE_PARAM).enqueue(callback);
    }

    public void moviesRequest(int genreId, DefaultCallback<Movies> callback){
        mApiService.getMoviesByGenre(genreId, Constants.API_KEY, Constants.LANGUAGE_PARAM,
                false, Constants.SORT_BY).enqueue(callback);
    }

    public void loadNextPage(int genreId, int page, DefaultCallback<Movies> callback){
        mApiService.getMoviesByGenreWithPage(genreId, Constants.API_KEY,
                Constants.LANGUAGE_PARAM, false, Constants.SORT_BY, ++page).enqueue(callback);
    }

}
