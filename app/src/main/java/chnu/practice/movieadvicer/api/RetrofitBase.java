package chnu.practice.movieadvicer.api;

import chnu.practice.movieadvicer.consts.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ваня on 15.02.2018.
 */

public class RetrofitBase {
    public Retrofit initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        return retrofit;
    }
}
