package com.modcom.medilabmemberapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import com.modcom.medilabmemberapp.helpers.PrefsHelper

class MemberSignUp2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_sign_up2)

        // find phone and email EditText and save to Prefs
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editPhone = findViewById<EditText>(R.id.editPhone)

        val email = editEmail.text
        val phone = editPhone.text

        // getting data from radio(radioMale -> "Male", radioFemale -> "Female")
        // find the two radio button
        val radioMale = findViewById<RadioButton>(R.id.radioMale)
        val radioFemale = findViewById<RadioButton>(R.id.radioFemale)

        // find register1 button and proceed to MemberSignUp2
        val register2 = findViewById<Button>(R.id.register2)
        register2.setOnClickListener {
            PrefsHelper.savePrefs(applicationContext, "email", email.toString())
            PrefsHelper.savePrefs(applicationContext, "phone", phone.toString())

            var gender = "N/A"
            if (radioMale.isChecked){
                gender = "Male"
                PrefsHelper.savePrefs(applicationContext, "gender", gender)
            }

            if (radioFemale.isChecked){
                gender = "Female"
                PrefsHelper.savePrefs(applicationContext, "gender", gender)
            }

            val intent = Intent(applicationContext, MemberSignUp3::class.java)
            startActivity(intent)
        }
    }
}