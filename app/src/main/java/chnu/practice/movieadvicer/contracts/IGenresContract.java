package chnu.practice.movieadvicer.contracts;

import android.app.Activity;

import chnu.practice.movieadvicer.models.GenreModel.Genre;
import chnu.practice.movieadvicer.models.GenreModel.Genres;

/**
 * Created by Ваня on 13.02.2018.
 */

public interface IGenresContract extends BaseContract{

    interface IView extends BaseContract.IView{
        void showGenres(Genres genres);
        void toMovieActivity(String genreName);
        Activity getViewActivity();
    }

    interface IPresenter extends BaseContract.IPresenter{
        void genresRequest();
        void moviesRequest(Genre genre);
        boolean checkPermission();
    }
}
