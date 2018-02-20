package chnu.practice.movieadvicer.contracts;

import android.content.Context;

import chnu.practice.movieadvicer.models.GenreModel.Genres;

/**
 * Created by Ваня on 13.02.2018.
 */

public interface IGenresContract extends BaseContract{

    interface IGenresView extends BaseContract.IView{
        void showGenres(Genres genres);
        void toMovieActivity();
    }

    interface IGenresPresenter extends BaseContract.IPresenter{
        void genresRequest();
        void moviesRequest(int genreId, Context context);
    }
}
