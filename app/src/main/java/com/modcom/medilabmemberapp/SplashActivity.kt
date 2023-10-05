package com.modcom.medilabmemberapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Delay splash for sometimes(5 seconds->)intent to screen 1
        Handler().postDelayed({
                              val intent = Intent(applicationContext,Screen1::class.java)
                                startActivity(intent)
                                finish()

        },5000)
    }
}