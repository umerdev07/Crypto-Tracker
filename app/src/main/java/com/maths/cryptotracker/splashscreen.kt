package com.maths.cryptotracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager

class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splashscreen)

        Handler(Looper.getMainLooper()).postDelayed({
             val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}