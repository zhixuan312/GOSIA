package csl.sia.com.gosia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends Activity {

    Thread splashTread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StartAnimations();
    }

    private void StartAnimations() {

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        ImageView iv = (ImageView) findViewById(R.id.splash_IV);
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 2200) {
                        sleep(100);
                        waited += 100;
                    }

                    Intent intent = new Intent(Splash.this, LandingActivity.class);
                    Splash.this.startActivity(intent);
                    Splash.this.finish();
                } catch (InterruptedException e) {
                } finally {
                    Splash.this.finish();
                }
            }
        };
        splashTread.start();
    }
}
