package chnu.practice.movieadvicer.app;

import android.content.Context;
import android.content.Intent;

import chnu.practice.movieadvicer.ui.activities.MainActivity;
import chnu.practice.movieadvicer.ui.activities.MovieActivity;


public class Navigation {

    public static void toMainScreen(Context context){
        toActivity(context, MainActivity.class);
    }

    public static void toMovieScreen(Context context){
        toActivity(context, MovieActivity.class);
    }

    private static void toActivity(Context context, Class<?> activityClass){
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }
}
