package chnu.practice.movieadvicer;

import chnu.practice.movieadvicer.contracts.BaseContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public abstract class DefaultCallback<T> implements Callback<T> {

    private BaseContract.IView view;

    public DefaultCallback(BaseContract.IView view) {
        this.view = view;
        view.showProgress();
    }

    @Override public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            onError(response.message());
        }
    }

    @Override public void onFailure(Call<T> call, Throwable throwable) {
        onError("Check your internet connection");
    }

    public abstract void onSuccess(final T response);

    private void onError(String message){
        view.hideProgress();
        view.showError(message);
    }
}