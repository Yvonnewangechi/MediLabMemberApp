package com.modcom.medilabmemberapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.modcom.medilabmemberapp.helpers.SQLiteCartHelper

class MyCart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)


    }

    // Inflate the Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clearCArt){
            val helper = SQLiteCartHelper(applicationContext)
            helper.clearCart()
            Toast.makeText(applicationContext, "Cart Cleared!", Toast.LENGTH_SHORT).show()
        }

        // Other Implementation Done Here...

        return true
    }
}