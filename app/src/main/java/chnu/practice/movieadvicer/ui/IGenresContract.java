package chnu.practice.movieadvicer.ui;

import android.content.Context;

import chnu.practice.movieadvicer.models.GenreModel.Genres;

/**
 * Created by Ваня on 13.02.2018.
 */

public interface IGenresContract {

    interface IGenresView{
        void showProgress();
        void hideProgress();
        void showError(String message);
        Context getViewContext();

    }

    interface IGenresPresenter{
        void genresRequest();
        void getGenres(Genres genres);
    }
}
