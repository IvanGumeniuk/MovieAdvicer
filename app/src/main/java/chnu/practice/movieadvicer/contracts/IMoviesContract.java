package chnu.practice.movieadvicer.contracts;

import chnu.practice.movieadvicer.models.MovieModel.Movies;
import chnu.practice.movieadvicer.models.MovieModel.Result;

/**
 * Created by Ваня on 24.02.2018.
 */

public interface IMoviesContract extends BaseContract {
    interface IView extends BaseContract.IView{
        void showMovies(Movies movies);
    }

    interface IPresenter extends BaseContract.IPresenter{
        void loadNextPage(int page);
        void refresh();
        void updateMoviesByGenre();
        void addFavorite(Result result);
        void removeFavorite(Result result);
    }
}
