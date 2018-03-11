package chnu.practice.movieadvicer.presenters;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import chnu.practice.movieadvicer.DefaultCallback;
import chnu.practice.movieadvicer.app.RetrofitBase;
import chnu.practice.movieadvicer.contracts.IGenresContract;
import chnu.practice.movieadvicer.dataSource.FileDataSource;
import chnu.practice.movieadvicer.models.GenreModel.Genre;
import chnu.practice.movieadvicer.models.GenreModel.Genres;
import chnu.practice.movieadvicer.models.MovieModel.Movies;

public class GenresPresenter implements IGenresContract.IPresenter{

    private IGenresContract.IView mView;
    private FileDataSource mDataSource;
    private RetrofitBase mRetrofit;

    public GenresPresenter(IGenresContract.IView gView) {
        this.mView = gView;
        mRetrofit = RetrofitBase.getInstance();
        mDataSource = FileDataSource.getInstance();
    }

    @Override
    public void genresRequest() {
        if (checkPermission()) {
            mRetrofit.genresRequest(new DefaultCallback<Genres>(mView) {
                @Override
                public void onSuccess(Genres response) {
                    mView.hideProgress();
                    mView.showGenres(response);
                }
            });
        } else {
            mView.hideProgress();
            mView.showToast("You need to allow permissions");
        }
    }

    @Override
    public void moviesRequest(final Genre genre) {
        mRetrofit.moviesRequest(genre.getId(), new DefaultCallback<Movies>(mView) {
            @Override
            public void onSuccess(Movies response) {
                mDataSource.saveMoviesByGenre(genre.getId(), response);
                mView.hideProgress();
                mView.toMovieActivity(genre.getName());
            }
        });
    }

    @Override
    public boolean checkPermission() {
        boolean result;
        if (ContextCompat.checkSelfPermission(mView.getViewActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(mView.getViewActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
            ActivityCompat.requestPermissions(mView.getViewActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    2);
            result = true;
            // mView.getViewActivity().onRequestPermissionsResult();
        } else {
            result = true;
        }
        return result;
    }

}
