package com.modcom.medilabmemberapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modcom.medilabmemberapp.adapters.LabTestCartAdapter
import com.modcom.medilabmemberapp.helpers.PrefsHelper
import com.modcom.medilabmemberapp.helpers.SQLiteCartHelper

class MyCart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)

        //1. We cannot show checkBtn when total cost is 0.0
        val cartHelper = SQLiteCartHelper(applicationContext)
        val checkoutBtn : Button = findViewById(R.id.checkout)

        if (cartHelper.totalCost() == 0.0){
            checkoutBtn.visibility = View.GONE
        }

        //2. Check cannot be proceeded if user is not logged in(token is Empty)
        checkoutBtn.setOnClickListener {
            val token = PrefsHelper.getPrefs(applicationContext, "refresh_token")
            if(token.isEmpty()){
                Toast.makeText(applicationContext, "Not Logged In", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(applicationContext, "Proceeding to Check Out", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext,CheckOutStep1::class.java)
                startActivity(intent)
            }

        } // end listener

        //3. Find the total Tv, and bind with the totalCost() from the SQLite class
        val total : TextView = findViewById(R.id.total)
        total.text = "Total: " + cartHelper.totalCost()

        //4. Find the Recyler:
        val recycler: RecyclerView = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(applicationContext)

        if(cartHelper.getNumItems() == 0){
            Toast.makeText(applicationContext, "Your Cart is Empty", Toast.LENGTH_SHORT).show()
        }

        else{
            val adapter = LabTestCartAdapter(applicationContext)
            adapter.setListItems(cartHelper.getAllItems())
            recycler.adapter = adapter
        }

    }

    // Inflate the Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clearCArt){
            val helper = SQLiteCartHelper(applicationContext)
            helper.clearCart()
            Toast.makeText(applicationContext, "Cart Cleared!", Toast.LENGTH_SHORT).show()
        }

        // Other Implementation Done Here...

        if (item.itemId == R.id.login){
            val intent = Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
        }

        if (item.itemId == R.id.back){
            val intent = Intent(applicationContext,HomeActivity::class.java)
            startActivity(intent)
        }

        return true
    }


    override fun onBackPressed() {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}