package lebelleami.com.beam.Controller;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import lebelleami.com.beam.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        /**
         * Declare name of font used for beam
         */
        TextView textView = findViewById(R.id.beam_text);
        ImageView imageView = findViewById(R.id.beam_icon);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transitions);
        textView.startAnimation(animation);
        imageView.startAnimation(animation);

        Typeface customFont = Typeface.createFromAsset(getAssets(),"font/montserrat_semibold.ttf");
        textView.setTypeface(customFont);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startActivity = new Intent(SplashScreen.this, IntroScreen.class);
                startActivity(startActivity);
                finish();
            }
        }, 5000);
    }
}
