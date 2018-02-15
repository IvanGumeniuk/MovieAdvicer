package chnu.practice.movieadvicer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import chnu.practice.movieadvicer.R;
import chnu.practice.movieadvicer.consts.Constants;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView splashImage = findViewById(R.id.splashImage);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splash_transition);
        splashImage.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainAct = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainAct);
                finish();
            }
        }, Constants.SPLASH_DELAY);
    }
}
