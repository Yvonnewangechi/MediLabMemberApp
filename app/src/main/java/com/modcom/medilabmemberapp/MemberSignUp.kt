package com.modcom.medilabmemberapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.modcom.medilabmemberapp.helpers.NetworkHelper
import com.modcom.medilabmemberapp.helpers.PrefsHelper

class MemberSignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_sign_up)

        // Check Internet
        if(NetworkHelper.isInternetConnected(applicationContext)){
            Toast.makeText(applicationContext, "You are Connected", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(applicationContext, "You are not Connected", Toast.LENGTH_SHORT).show()
            // create a dialog box to alert no internet connection
            showAlertDialog()
        }



        // get data from the 3 editTexts
        val editSurname = findViewById<EditText>(R.id.editSurname)
        val editOthers = findViewById<EditText>(R.id.editOthers)
        val editPassword = findViewById<EditText>(R.id.editPassword)

        // get data
        val surname = editSurname.text
        val others= editOthers.text
        val password = editPassword.text

            // find register1 button and proceed to MemberSignUp2
        val register1 = findViewById<Button>(R.id.register1)
        register1.setOnClickListener {

            // save surname, others, password to Prefs
            PrefsHelper.savePrefs(applicationContext, "surname", surname.toString())
            PrefsHelper.savePrefs(applicationContext, "others", others.toString())
            PrefsHelper.savePrefs(applicationContext, "password", password.toString())

            // save surname, others, password
            val intent = Intent(applicationContext, MemberSignUp2::class.java)
            startActivity(intent)
        }


    }

    private fun showAlertDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Internet Check")
            .setMessage("Please Check Your Internet Connection")
            .setPositiveButton("Yes"){dialog, which ->
                dialog.dismiss()
            }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}


// To create icon we use vector assets