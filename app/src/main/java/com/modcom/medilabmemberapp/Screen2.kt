package com.modcom.medilabmemberapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Screen2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen2)

        val next = findViewById<TextView>(R.id.next1)
        next.setOnClickListener {
            // Intent message to Screen2
            val intent = Intent(applicationContext,BottomSheet::class.java)
            startActivity(intent)

            // Create Screen3 Activity with different design
            // Link an intent from Screen2 Activity to Screen3
            // Link Screen3 Activity to MainActivity
            // Add an id to the skip text, provide a listener and intent direct to Main Activity
        }
    }
}