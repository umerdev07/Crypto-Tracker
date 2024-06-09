package com.maths.cryptotracker

import android.content.Intent
import android.os.AsyncTask
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
        HeavyTask();

    }

    private fun HeavyTask() {
       LongOperation().execute()
    }
    private open inner class LongOperation : AsyncTask<String? , Void? , String?>(){
        override fun doInBackground(vararg params: String?): String? {

            var i = 0
            while (i<=6){
                try{
                    Thread.sleep(1000)
                }catch (e:Exception){
                    Thread.interrupted()
                }
                i++
            }
            return "result"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val intent = Intent(this@splashscreen,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}