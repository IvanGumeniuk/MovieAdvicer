package chnu.practice.movieadvicer.presenters;

import android.content.Context;
import android.widget.Toast;

import chnu.practice.movieadvicer.DefaultCallback;
import chnu.practice.movieadvicer.api.ApiService;
import chnu.practice.movieadvicer.app.RetrofitBase;
import chnu.practice.movieadvicer.consts.Constants;
import chnu.practice.movieadvicer.contracts.IGenresContract;
import chnu.practice.movieadvicer.models.GenreModel.Genres;
import chnu.practice.movieadvicer.models.MovieModel.Movies;
import okhttp3.ResponseBody;

public class GenresPresenter extends BasePresenter implements IGenresContract.IGenresPresenter {

    private IGenresContract.IGenresView mView;
    private ApiService mApiRequest;
    private RetrofitBase mRetrofitBase;

    public GenresPresenter(IGenresContract.IGenresView gView) {
        this.mView = gView;
        mRetrofitBase = RetrofitBase.getInstance();
        mApiRequest = mRetrofitBase.getRetrofit().create(ApiService.class);
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
    public void moviesRequest(int genreId, final Context context) {
        mView.showProgress();
        mApiRequest.getMoviesByGenre(genreId, Constants.API_KEY, Constants.LANGUAGE_PARAM,
                false, Constants.SORT_BY).enqueue(new DefaultCallback<Movies>() {
            @Override
            public void onSuccess(Movies response) {
                Toast.makeText(context, "Success "+ response.totalPages, Toast.LENGTH_SHORT).show();
                mView.hideProgress();
            }

            @Override
            public void onError(ResponseBody body, String message) {
                mView.hideProgress();
                mView.showError(message);
            }
        });
    }


}
