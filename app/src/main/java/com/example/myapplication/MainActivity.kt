package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Vreme trajanja Splash ekrana u milisekundama
    private val SPLASH_SCREEN=2000L;

    private lateinit var topAnim: Animation
    private lateinit var bottomAnim: Animation
    private lateinit var image: ImageView
    private lateinit var slogan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Učitavanje animacija iz resursa
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        image=findViewById(R.id.overlayImageView);
        slogan=findViewById(R.id.textView);

        // Postavljanje animacija na elemente
        image.setAnimation(bottomAnim);
        slogan.setAnimation(topAnim);

        // Postavljanje odloženog prelaza na sledeći ekran pomoću Handler-a
        Handler().postDelayed({
            val intent = Intent(this, MainActivity_Dashboard::class.java)
            startActivity(intent);
            finish();
        }, SPLASH_SCREEN)
    }
}
