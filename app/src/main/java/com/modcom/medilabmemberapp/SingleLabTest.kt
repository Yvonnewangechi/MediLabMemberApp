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
        val test_id = intent.extras?.getInt("test_id")
        //========= to be used in the cart

        val tvTestName: TextView = findViewById(R.id.tvLabTestName)
        val test_name = intent.extras?.getString("test_name")
        tvTestName.text = test_name
        //=============

        val tvTestCost: TextView = findViewById(R.id.cost)
        val test_cost = intent.extras?.getInt("test_cost")
        tvTestCost.text = "KES" + test_cost.toString()
        //=============

        val tvTestDiscount: TextView = findViewById(R.id.discount)
        val test_discount= intent.extras?.getInt("test_discount")
        tvTestDiscount.text = "KES" + test_discount.toString()

        val tvTestDesc: TextView = findViewById(R.id.textDec)
        val test_desc = intent.extras?.getString("test_description")
        tvTestDesc.text = test_desc
        //=============

        val lab_id = intent.extras?.getInt("lab_id")


        val cart = findViewById<Button>(R.id.cart)
        cart.setOnClickListener {

            val cartHelper = SQLiteCartHelper(applicationContext)
            cartHelper.insert(test_id, test_name, test_cost, test_desc,lab_id)
            val intent = Intent(applicationContext, MyCart::class.java)
            startActivity(intent)
        }


    }
}