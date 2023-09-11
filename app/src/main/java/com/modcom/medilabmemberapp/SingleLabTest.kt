package com.modcom.medilabmemberapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.modcom.medilabmemberapp.helpers.SQLiteCartHelper

class SingleLabTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_lab_test)

        // Get Data from the Intent Extras and bind them to the views
        val test_id = intent.extras?.getString("test_id")
        //========= to be used in the cart

        val tvTestName: TextView = findViewById(R.id.tvLabTestName)
        val test_name = intent.extras?.getString("test_name")
        tvTestName.text = test_name
        //=============

        val cart = findViewById<Button>(R.id.cart)
        cart.setOnClickListener {
            val intent = Intent(applicationContext, MyCart::class.java)
            startActivity(intent)
        }


    }
}