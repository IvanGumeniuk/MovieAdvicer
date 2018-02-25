package chnu.practice.movieadvicer.app;

import chnu.practice.movieadvicer.consts.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ваня on 20.02.2018.
 */

public class RetrofitBase {
    private static RetrofitBase sInstance;
    private Retrofit retrofit;

    private RetrofitBase(){
        retrofitInitialization();
    }

    public static RetrofitBase getInstance(){
        if(sInstance == null){
            sInstance = new RetrofitBase();
        }
        return sInstance;
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }

    private void retrofitInitialization(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
