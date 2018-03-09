package chnu.practice.movieadvicer.presenters;

import chnu.practice.movieadvicer.DefaultCallback;
import chnu.practice.movieadvicer.app.RetrofitBase;
import chnu.practice.movieadvicer.contracts.IMoviesContract;
import chnu.practice.movieadvicer.dataSource.FileDataSource;
import chnu.practice.movieadvicer.models.MovieModel.Movies;
import chnu.practice.movieadvicer.models.MovieModel.Result;

public class MoviesPresenter implements IMoviesContract.IPresenter {

    private IMoviesContract.IView mView;
    private FileDataSource mDataSource;
    private RetrofitBase mRetrofitBase;

    public MoviesPresenter(IMoviesContract.IView mView) {
        this.mView = mView;
        mRetrofitBase = RetrofitBase.getInstance();
        mDataSource = FileDataSource.getInstance();
        mView.showMovies(mDataSource.getMoviesByGenre());
    }


    @Override
    public void loadNextPage(int page) {
        mRetrofitBase.loadNextPage(mDataSource.getGenreId(), page, new DefaultCallback<Movies>(mView) {
            @Override
            public void onSuccess(Movies response) {
                mDataSource.addNewPage(response);
                mView.showMovies(mDataSource.getMoviesByGenre());
                mView.hideProgress();
            }
        });
    }

    @Override
    public void refresh() {
        mRetrofitBase.moviesRequest(mDataSource.getGenreId(), new DefaultCallback<Movies>(mView) {
            @Override
            public void onSuccess(Movies response) {
                mDataSource.saveMoviesByGenre(mDataSource.getGenreId(), response);
                mView.showMovies(mDataSource.getMoviesByGenre());
                mView.hideProgress();
            }
        });
    }

    @Override
    public void updateMoviesByGenre() {
        mDataSource.updateGenresMoviesFile();
    }

    @Override
    public void addFavorite(Result result) {
        mDataSource.addFavoriteMovie(result);
    }

    @Override
    public void removeFavorite(Result result) {
        mDataSource.removeFavoriteMovie(result);
    }

}
