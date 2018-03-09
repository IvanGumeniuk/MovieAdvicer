package chnu.practice.movieadvicer.contracts;

import android.app.Activity;

import chnu.practice.movieadvicer.models.GenreModel.Genres;

/**
 * Created by Ваня on 13.02.2018.
 */

public interface IGenresContract extends BaseContract{

    interface IView extends BaseContract.IView{
        void showGenres(Genres genres);
        void toMovieActivity();
        Activity getViewActivity();
    }

    interface IPresenter extends BaseContract.IPresenter{
        void genresRequest();
        void moviesRequest(int genreId);
        boolean checkPermission();
    }
}
