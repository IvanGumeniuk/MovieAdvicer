package chnu.practice.movieadvicer.app;

import android.content.Context;
import android.content.Intent;

import chnu.practice.movieadvicer.ui.activities.MainActivity;
import chnu.practice.movieadvicer.ui.activities.MovieActivity;


public class Navigation {

    public static void toMainScreen(Context context){
        toActivity(context,null,null, MainActivity.class);
    }

    public static void toMovieScreen(Context context,  String key, String data){
        toActivity(context, key, data, MovieActivity.class);
    }

    private static void toActivity(Context context, String key, String data, Class<?> activityClass){
        Intent intent = new Intent(context, activityClass);
        intent.putExtra(key, data);
        context.startActivity(intent);
    }
}
