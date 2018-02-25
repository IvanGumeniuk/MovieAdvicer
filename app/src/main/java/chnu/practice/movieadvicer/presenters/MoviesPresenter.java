package chnu.practice.movieadvicer.presenters;

import chnu.practice.movieadvicer.DefaultCallback;
import chnu.practice.movieadvicer.api.ApiService;
import chnu.practice.movieadvicer.app.RetrofitBase;
import chnu.practice.movieadvicer.consts.Constants;
import chnu.practice.movieadvicer.contracts.IMoviesContract;
import chnu.practice.movieadvicer.dataSource.FileDataSource;
import chnu.practice.movieadvicer.models.MovieModel.Movies;
import okhttp3.ResponseBody;

/**
 * Created by Ваня on 24.02.2018.
 */

public class MoviesPresenter implements IMoviesContract.IPresenter,
        FileDataSource.OnDataChangeListener{

    private IMoviesContract.IView mView;
    private ApiService mApiService;
    private FileDataSource mDataSource;

    public MoviesPresenter(IMoviesContract.IView mView) {
        this.mView = mView;
        RetrofitBase mRetrofitBase = RetrofitBase.getInstance();
        mApiService = mRetrofitBase.getRetrofit().create(ApiService.class);
        mDataSource = FileDataSource.getInstance(this);
    }


    @Override
    public void loadNextPage(int page) {
       mApiService.getMoviesByGenreWithPage(mDataSource.getGenreId(), Constants.API_KEY,
               Constants.LANGUAGE_PARAM, false, Constants.SORT_BY, ++page).enqueue(new DefaultCallback<Movies>() {
           @Override
           public void onSuccess(Movies response) {
               mDataSource.updateMovies(response);
               mView.showMovies(mDataSource.getMoviesByGenre());
           }

           @Override
           public void onError(ResponseBody body, String message) {
                mView.showError(message);
           }
       });
    }


    @Override
    public void onDataSaved(String message) {

    }

    @Override
    public void onDataOpened(Movies results) {
        mView.showProgress();
        mView.showMovies(results);
        mView.hideProgress();
    }
}
