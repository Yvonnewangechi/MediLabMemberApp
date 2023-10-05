package com.modcom.medilabmemberapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.modcom.medilabmemberapp.helpers.PrefsHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CheckOutStep1 : AppCompatActivity() {
    //==late initializations

    private lateinit var btnDatePicker :Button
    private lateinit var editDatePicker :EditText

    private lateinit var btnTimePicker :Button
    private lateinit var editTimePicker :EditText


    //=== show datePicker()
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
                editDatePicker.setText(selectedDate)

                PrefsHelper.savePrefs(applicationContext, "dob", selectedDate)

            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }


    //== show timePicker()
    fun showTimePicker(){
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            this,
            timeSetListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true)
        timePickerDialog.show()
    }// ens ShowDatePiccker()

    private val timeSetListener = TimePickerDialog.OnTimeSetListener{
            _,hourOfDay,minute ->
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay)
        calendar.set(Calendar.MINUTE,minute)

        val sdf = SimpleDateFormat("hh:mm", Locale.getDefault())
        val selectedTime = sdf.format(calendar.time)
        editTimePicker.setText(selectedTime)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out_step1)

        //== called here
        btnDatePicker  = findViewById(R.id.buttonDatePicker)
        editDatePicker = findViewById(R.id.editTextDate)
        btnDatePicker.setOnClickListener {
            showDatePickerDialog()

        }

        btnTimePicker  = findViewById(R.id.buttonTimePicker)
        editTimePicker = findViewById(R.id.editTextTime)
        btnTimePicker.setOnClickListener {
            showTimePicker()

        }


      //proceed to step 2

        val btnProceedStep2 :Button = findViewById(R.id.proceedStep2Btn)
        btnProceedStep2.setOnClickListener{
            //PLACE
            val home :RadioButton = findViewById(R.id.radioHome)
            val hospital :RadioButton = findViewById(R.id.radioHospital)
            var where_taken = ""
            if(home.isChecked){
                where_taken = "Home"
            }
            if(hospital.isChecked){
                where_taken = "Hospital"
            }
            //booked for
            val self :RadioButton = findViewById(R.id.radioSelf)
            val Dependant :RadioButton = findViewById(R.id.radioDependant)
            var booked_for = ""
            if (self.isChecked){
                booked_for ="Self"

            }
            if (Dependant.isChecked){
                booked_for = "Dependant"

            }
            //DATE AND TIME
            val date = editDatePicker.text.toString()
            val time = editTimePicker.text.toString()

            //SAVE TO PREFS AND PROCEED TO GPS

            if (date.isEmpty() || time.isEmpty() || where_taken.isEmpty() || booked_for.isEmpty()){
                Toast.makeText(applicationContext, "Empty Fields", Toast.LENGTH_SHORT).show()
            }

            else{
                //save to prefs
                PrefsHelper.savePrefs(applicationContext,"where_taken",where_taken)
                PrefsHelper.savePrefs(applicationContext,"booked_for",booked_for)
                PrefsHelper.savePrefs(applicationContext,"date",date)
                PrefsHelper.savePrefs(applicationContext,"time",time)

                if (isLocationEnabled()) {
                        Toast.makeText(applicationContext, "GPS ON", Toast.LENGTH_SHORT).show()
                    if (booked_for== "Self"){
                        PrefsHelper.savePrefs(applicationContext,"dependant_id","")
                        val intent = Intent(applicationContext,CheckOutStep2::class.java)
                        startActivity(intent)
                    }
                    else{
                        val intent = Intent(applicationContext,DependantsActivity::class.java)
                        startActivity(intent)

                    }
                }
                else{
                    Toast.makeText(applicationContext, "GPS OFF", Toast.LENGTH_SHORT).show()
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }

            }

        }

    }// END ONCREATE

    private fun isLocationEnabled():Boolean{
        var locationManager:LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

}