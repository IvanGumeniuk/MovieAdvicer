package chnu.practice.movieadvicer.presenters;

import chnu.practice.movieadvicer.DefaultCallback;
import chnu.practice.movieadvicer.api.ApiService;
import chnu.practice.movieadvicer.app.RetrofitBase;
import chnu.practice.movieadvicer.consts.Constants;
import chnu.practice.movieadvicer.contracts.IGenresContract;
import chnu.practice.movieadvicer.dataSource.FileDataSource;
import chnu.practice.movieadvicer.models.GenreModel.Genres;
import chnu.practice.movieadvicer.models.MovieModel.Movies;
import okhttp3.ResponseBody;

public class GenresPresenter implements IGenresContract.IPresenter,
        FileDataSource.OnDataChangeListener {

    private IGenresContract.IView mView;
    private ApiService mApiRequest;
    private FileDataSource mDataSource;

    public GenresPresenter(IGenresContract.IView gView) {
        this.mView = gView;
        RetrofitBase mRetrofitBase = RetrofitBase.getInstance();
        mApiRequest = mRetrofitBase.getRetrofit().create(ApiService.class);
        mDataSource = FileDataSource.getInstance(this);
    }

    @Override
    public void genresRequest() {
        mView.showProgress();

        mApiRequest.getGenres(Constants.API_KEY, Constants.LANGUAGE_PARAM).enqueue(new DefaultCallback<Genres>() {
            @Override
            public void onSuccess(Genres response) {
                mView.hideProgress();
                mView.showGenres(response);
            }

            @Override
            public void onError(ResponseBody body, String message) {
                mView.hideProgress();
                mView.showError(message);
            }
        });
    }

    @Override
    public void moviesRequest(final int genreId) {
        mView.showProgress();
        mApiRequest.getMoviesByGenre(genreId, Constants.API_KEY, Constants.LANGUAGE_PARAM,
                false, Constants.SORT_BY).enqueue(new DefaultCallback<Movies>() {
            @Override
            public void onSuccess(Movies response) {
                mDataSource.saveMoviesByGenre(genreId, response);
                mView.hideProgress();
                mView.toMovieActivity();
            }

            @Override
            public void onError(ResponseBody body, String message) {
                mView.hideProgress();
                mView.showError(message);
            }
        });
    }

    @Override
    public void onDataSaved(String message) {
        mView.showToast(message);
    }

    @Override
    public void onDataOpened(Movies results) {
        mView.showToast("opened");
    }
}
