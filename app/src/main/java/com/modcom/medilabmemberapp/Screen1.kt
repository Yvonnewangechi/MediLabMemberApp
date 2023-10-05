package com.modcom.medilabmemberapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.modcom.medilabmemberapp.helpers.CheckOnBoardingClicked
import com.modcom.medilabmemberapp.helpers.PrefsHelper

class Screen1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen1)
        val check = CheckOnBoardingClicked(applicationContext)

        if ( check.checkOnBardingClicked()){
            val intent = Intent(applicationContext,BottomSheet::class.java)
            startActivity(intent)
            finishAffinity()

        }
        else{
            val next = findViewById<TextView>(R.id.next1)
            next.setOnClickListener {
                // Intent message to Screen2
                //save a value on prefs
                PrefsHelper.savePrefs(applicationContext,"screen1Clicked","true")

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
}