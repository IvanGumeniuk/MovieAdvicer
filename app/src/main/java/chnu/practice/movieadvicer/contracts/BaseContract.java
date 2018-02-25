package chnu.practice.movieadvicer.contracts;

/**
 * Created by Ваня on 20.02.2018.
 */

public interface BaseContract {
     interface IView{
        void showProgress();
        void hideProgress();
        void showToast(String message);
        void showError(String message);
    }

    interface IPresenter{

    }
}
