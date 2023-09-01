package com.modcom.medilabmemberapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.get
import com.modcom.medilabmemberapp.constants.Constants
import com.modcom.medilabmemberapp.helpers.ApiHelper
import com.modcom.medilabmemberapp.helpers.PrefsHelper
import org.json.JSONArray
import org.json.JSONObject
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

            // api -> String
            // data(body) -> JSONObject
            // CallBack Interface -> JSONObject
            val api = Constants.BASE_URL + "/member_signup"
            val body = JSONObject()
            body.put("surname",surname.toString() )
            body.put("others",others.toString() )
            body.put("email",email.toString() )
            body.put("phone",phone.toString() )
            body.put("dob",dob.toString() )
            body.put("password",password.toString() )
            body.put("gender",gender.toString() )
            body.put("location_id",location_id.toString() )

            // create an object from ApiHelper class
            val helper = ApiHelper(applicationContext)
            helper.post(api, body, object: ApiHelper.CallBack{
                override fun onSuccess(result: JSONArray?) {
                    TODO("Not yet implemented")
                }

                override fun onSuccess(result: JSONObject?) {
                    Toast.makeText(applicationContext, "${result.toString()}", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(result: String?) {
                    Toast.makeText(applicationContext, "${result.toString()}", Toast.LENGTH_SHORT).show()
                }
            })



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
