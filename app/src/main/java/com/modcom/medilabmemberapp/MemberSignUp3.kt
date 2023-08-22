package com.modcom.medilabmemberapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.get
import com.modcom.medilabmemberapp.helpers.PrefsHelper
import java.util.*

class MemberSignUp3 : AppCompatActivity() {
    private lateinit var buttonDatePicker: Button
    private lateinit var editTextDate: EditText
    private lateinit var spinner : Spinner
    private lateinit var locationSelected : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_sign_up3)

        // find DatePicker Button and Show Date Picker Dialog
        buttonDatePicker = findViewById(R.id.buttonDatePicker)
        editTextDate = findViewById(R.id.editTextDate)

        // assign onClickListener to buttonDatePicker
        buttonDatePicker.setOnClickListener {
            showDatePickerDialog()
        }

        // find the spinner and assign it to variable spinner
        spinner = findViewById(R.id.spinner)
        locationSelected = findViewById(R.id.locationSelected)

        val locations = listOf(1, 2, 3, 4, 5)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, locations)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // checking the data item that is selected and saving it on a variable
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                PrefsHelper.savePrefs(applicationContext, "location_id", selectedItem)
                
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Nothing Selected", Toast.LENGTH_SHORT).show()
            }

        }




        // find register1 button and proceed to MemberSignUp2
        val register3 = findViewById<Button>(R.id.register3)
        register3.setOnClickListener {

            // Get all the Member Details from and Prefs and Submit to the API(/member_signup)
            val surname = PrefsHelper.getPrefs(applicationContext, "surname")
            val others = PrefsHelper.getPrefs(applicationContext, "others")
            val gender = PrefsHelper.getPrefs(applicationContext, "gender")
            val email = PrefsHelper.getPrefs(applicationContext, "email")
            val phone = PrefsHelper.getPrefs(applicationContext, "phone")
            val dob = PrefsHelper.getPrefs(applicationContext, "dob")
            val password = PrefsHelper.getPrefs(applicationContext, "password")
            val location_id = PrefsHelper.getPrefs(applicationContext, "location_id")


            Toast.makeText(applicationContext, "Surname : $surname", Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext, "Others : $others", Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext, "Gender : $gender", Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext, "Email : $email", Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext, "Phone : $phone", Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext, "DOB : $dob", Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext, "Password : $password", Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext, "Location ID : $location_id", Toast.LENGTH_SHORT).show()

        }

    } // end onCreate()

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
                // Handle the selected date
                val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                editTextDate.setText(selectedDate)

                PrefsHelper.savePrefs(applicationContext, "dob", selectedDate)

            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}
