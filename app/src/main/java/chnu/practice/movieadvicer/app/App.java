package chnu.practice.movieadvicer.app;

import android.app.Application;

/**
 * Created by Ваня on 19.02.2018.
 */

public class App extends Application {
    private static App sInstance;

    public static App getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }


}
