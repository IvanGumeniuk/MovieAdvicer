package chnu.practice.movieadvicer.presenters;

import android.support.v7.widget.RecyclerView;

import chnu.practice.movieadvicer.adapters.GenreRecyclerAdapter;
import chnu.practice.movieadvicer.api.ApiService;
import chnu.practice.movieadvicer.api.RetrofitBase;
import chnu.practice.movieadvicer.consts.Constants;
import chnu.practice.movieadvicer.models.GenreModel.Genres;
import chnu.practice.movieadvicer.ui.IGenresContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenresPresenter implements IGenresContract.IGenresPresenter {

    private IGenresContract.IGenresView gView;
    private ApiService apiRequest;
    private RecyclerView recyclerView;


    public GenresPresenter(IGenresContract.IGenresView gView, RecyclerView recyclerView) {
        this.gView = gView;
        apiRequest = new RetrofitBase().initRetrofit().create(ApiService.class);
        this.recyclerView = recyclerView;
    }


    @Override
    public void genresRequest() {
        gView.showProgress();

        apiRequest.getGenres(Constants.API_KEY, Constants.LANGUAGE_PARAM).enqueue(new Callback<Genres>() {
            @Override
            public void onResponse(Call<Genres> call, Response<Genres> response) {
                getGenres(response.body());
            }

            @Override
            public void onFailure(Call<Genres> call, Throwable t) {
                gView.showError(t.getMessage());
            }
        });

        gView.hideProgress();
    }

    @Override
    public void getGenres(Genres genres) {
        recyclerView.setAdapter(new GenreRecyclerAdapter(genres, gView.getViewContext()));
    }


}
