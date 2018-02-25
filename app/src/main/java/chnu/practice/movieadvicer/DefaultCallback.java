package chnu.practice.movieadvicer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public abstract class DefaultCallback<T> implements Callback<T> {

    @Override public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            onError(response.errorBody(), response.message());
        }
    }

    @Override public void onFailure(Call<T> call, Throwable throwable) {
        onError(null, "Check your internet connection");
    }

    public abstract void onSuccess(final T response);

    public abstract void onError(ResponseBody body, String message);
}