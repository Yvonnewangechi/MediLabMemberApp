package com.modcom.medilabmemberapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.modcom.medilabmemberapp.helpers.PrefsHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // find the 3 editTexts and get data from them
        val editName = findViewById<EditText>(R.id.name)
        val editAge = findViewById<EditText>(R.id.age)
        val editCourse = findViewById<EditText>(R.id.course)

        val name = editName.text
        val age = editAge.text
        val course = editCourse.text

        // find button save and trigger save operation
        val buttonSave = findViewById<Button>(R.id.save)
        buttonSave.setOnClickListener {
            PrefsHelper.savePrefs(applicationContext, "name", name.toString())
            PrefsHelper.savePrefs(applicationContext, "age", age.toString())
            PrefsHelper.savePrefs(applicationContext, "course", course.toString())
            Toast.makeText(applicationContext, "Saved Successfully", Toast.LENGTH_SHORT).show()
        }


        // find the button get and display the data on a toast
        val getButton = findViewById<Button>(R.id.get)
        getButton.setOnClickListener {
            val receivedName = PrefsHelper.getPrefs(applicationContext, "name")
            val receivedAge = PrefsHelper.getPrefs(applicationContext, "age")
            val receivedCourse = PrefsHelper.getPrefs(applicationContext, "course")

            Toast.makeText(applicationContext, "Your name is: $receivedName", Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext, "Your age is: $receivedAge", Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext, "Your course is: $receivedCourse", Toast.LENGTH_SHORT).show()

        }

        // find the clear button and clear the preference




    }
}

// Shared Preferences: Temporary Storage on a application
// Facebook, save username and password
// Data is available, even if the app is closed, powered off until the preference is cleared