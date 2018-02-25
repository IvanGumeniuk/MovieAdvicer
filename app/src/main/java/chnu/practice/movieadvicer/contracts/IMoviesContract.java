package chnu.practice.movieadvicer.contracts;

import chnu.practice.movieadvicer.models.MovieModel.Movies;

/**
 * Created by Ваня on 24.02.2018.
 */

public interface IMoviesContract extends BaseContract {
    interface IView extends BaseContract.IView{
        public void showMovies(Movies movies);
    }

    interface IPresenter extends BaseContract.IPresenter{
        public void loadNextPage(int page);
    }
}
