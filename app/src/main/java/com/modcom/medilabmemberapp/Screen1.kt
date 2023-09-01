package com.modcom.medilabmemberapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Screen1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen1)

        val next = findViewById<TextView>(R.id.next1)
        next.setOnClickListener {
            // Intent message to Screen2
            val intent = Intent(applicationContext,Screen2::class.java)
            startActivity(intent)
        }

        val skip = findViewById<TextView>(R.id.skip)
        skip.setOnClickListener {
            // Intent message to Screen2
//            val intent = Intent(applicationContext,LoginActivity::class.java)
//            startActivity(intent)

            val intent = Intent(applicationContext,LabTestsActivity::class.java)
            startActivity(intent)
        }

    }
}