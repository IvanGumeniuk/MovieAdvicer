package chnu.practice.movieadvicer.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import chnu.practice.movieadvicer.R;
import chnu.practice.movieadvicer.app.Navigation;

public class SplashActivity extends BaseActivity {

    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Navigation.toMainScreen(SplashActivity.this);
            SplashActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView splashImage = findViewById(R.id.splashImage);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_transition);
        splashImage.startAnimation(animation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }
}
