package com.modcom.medilabmemberapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlin.random.Random

class CompleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete)

        //Research aon a kotlin function that generates random characters with numbers and letters(Invoice no)
        val completeBtn :Button = findViewById(R.id.btnHome)
        completeBtn.setOnClickListener {
            val intent = Intent(applicationContext,HomeActivity::class.java)
        }




    }



}