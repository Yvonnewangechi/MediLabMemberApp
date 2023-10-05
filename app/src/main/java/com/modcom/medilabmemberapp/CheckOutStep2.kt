package com.modcom.medilabmemberapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.modcom.medilabmemberapp.constants.Constants
import com.modcom.medilabmemberapp.helpers.ApiHelper
import com.modcom.medilabmemberapp.helpers.NetworkHelper
import com.modcom.medilabmemberapp.helpers.PrefsHelper
import com.modcom.medilabmemberapp.helpers.SQLiteCartHelper
import org.json.JSONArray
import org.json.JSONObject
import kotlin.random.Random

class CheckOutStep2 : AppCompatActivity() {

    // CONVERT COORDINATES TO STRING LOCATION
    fun getAddress(latLng: LatLng) : String {

        val geocoder = Geocoder(this)
        val list = geocoder.getFromLocation(latLng.latitude, latLng.longitude,1)

        return list!![0].getAddressLine(0)
    }

    // REQUEST LOCATION
    fun requestLocation(){
        if (ActivityCompat.checkSelfPermission(applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION), 123)
        } // end if

        else{
            Toast.makeText(applicationContext, "Location Permission Granted!", Toast.LENGTH_SHORT).show()
            getLocation()
        }

    } // end function

    // GET LOCATION
    @SuppressLint("MissingPermission")
    fun getLocation(){
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                    location ->
                location?.let {
                    editLatitude.setText(it.latitude.toString())
                    editLongitude.setText(it.longitude.toString())

                    val place = getAddress(LatLng(it.latitude, it.longitude));
                    Toast.makeText(applicationContext, "here $place",
                        Toast.LENGTH_SHORT).show()
                    //Put the place ia TextView
                    val location = findViewById<TextView>(R.id.location)
                    location.text = " Your Current Location \n $place"
                    requestNewLocation()
                    //Put button when  I click on that button.
                    //It intents to Maps and shows that Location.
                    //...................
                    //Interfaces.
                    //JS - Advanced


                } ?: run {
                    Toast.makeText(applicationContext, "Searching Location",
                        Toast.LENGTH_SHORT).show()
                    requestNewLocation()
                } //end run
            }//end success
            .addOnFailureListener { e ->
                Toast.makeText(applicationContext, "Error $e", Toast.LENGTH_SHORT).show()
                requestNewLocation()
            }//end Failure
    }//end function

    lateinit var mLocationCallback: LocationCallback
    @SuppressLint("MissingPermission")
    fun requestNewLocation(){

        val mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 10000
        mLocationRequest.fastestInterval = 10000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationCallback = object : LocationCallback(){
            override fun onLocationResult(result: LocationResult) {
                for(location in result.locations){
                    if (location!=null){
                        editLatitude.setText(location.latitude.toString())
                        editLongitude.setText(location.longitude.toString())

                        PrefsHelper.savePrefs(applicationContext, "latitude", editLatitude.text.toString())
                        PrefsHelper.savePrefs(applicationContext, "longitude", editLongitude.text.toString())
                        btnComplete.visibility = View.VISIBLE
                        getlocation.visibility = View.GONE

                    }//end if
                    else {
                        Toast.makeText(applicationContext, "Check GPS",
                            Toast.LENGTH_SHORT).show()
                    }//end else
                }//end for
            }//end result
        }//end call back

        fusedLocationClient.requestLocationUpdates(mLocationRequest,
            mLocationCallback, Looper.getMainLooper())

    }//end function


    // function o remove + from phone
    fun removePlus(phone: String, character: String) : String {
        val modifiedPhone = phone.replace(character.toString(), "")
        return  modifiedPhone
    }

    lateinit var editLatitude: EditText
    lateinit var editLongitude: EditText
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var getlocation: Button
    lateinit var btnComplete: Button

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out_step2)
        // Check Internet
        if(NetworkHelper.isInternetConnected(applicationContext)){
            Toast.makeText(applicationContext, "You are Connected", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(applicationContext, "You are not Connected", Toast.LENGTH_SHORT).show()
            // create a dialog box to alert no internet connection
            showAlertDialog()
        }

        editLatitude = findViewById(R.id.editTextLatitude)
        editLongitude = findViewById(R.id.editTextLongitude)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getlocation = findViewById(R.id.getLocation)

        getlocation.setOnClickListener {
            requestLocation()
        }

        btnComplete = findViewById(R.id.Complete)
        btnComplete.setOnClickListener {

            // Book Done Here
            val token = PrefsHelper.getPrefs(applicationContext, "refresh_token")
            if(token.isEmpty()){
                Toast.makeText(applicationContext, "Not Logged In", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                //Implement Book
             val cartHelper = SQLiteCartHelper(applicationContext)
                val items = cartHelper.getAllItems()
                val invoice_no = generateRandomString(10).uppercase()
                items.forEachIndexed{index,labtests ->
                    val test_id = labtests.test_id
                    val lab_id = labtests.lab_id

                    val api = Constants.BASE_URL + "/make_booking"

                    // Get all the payloads from Prefs
                    val member_id = PrefsHelper.getPrefs(applicationContext, "LoggedMember_id")
                    Log.d("member_id", member_id)
                    val where_taken = PrefsHelper.getPrefs(applicationContext, "where_taken")
                    val booked_for = PrefsHelper.getPrefs(applicationContext, "booked_for")
                    val date = PrefsHelper.getPrefs(applicationContext, "date")
                    val time = PrefsHelper.getPrefs(applicationContext, "time")
                    // Self -> "", // Dependent
                    val dependant_id = PrefsHelper.getPrefs(applicationContext, "dependant_id")


                    val latitude = PrefsHelper.getPrefs(applicationContext, "latitude")
                    val longitude = PrefsHelper.getPrefs(applicationContext, "longitude")


                    val payload = JSONObject()
                    payload.put("member_id", member_id)
                    payload.put("where_taken", where_taken)
                    payload.put("booked_for", booked_for)
                    payload.put("appointment_date", date)
                    payload.put("appointment_time", time)
                    payload.put("dependant_id", dependant_id)
                    payload.put("lab_id", lab_id)
                    payload.put("test_id", test_id)
                    payload.put("latitude", latitude)
                    payload.put("longitude", longitude)
                    payload.put("invoice_no", invoice_no)

                    val helper = ApiHelper(applicationContext)
                    helper.post(api, payload, object:  ApiHelper.CallBack{
                        override fun onSuccess(result: JSONArray?) {
                            TODO("Not yet implemented")
                        }

                        override fun onSuccess(result: JSONObject?) {
                            Toast.makeText(applicationContext, "$result", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, CompleteActivity::class.java)
                            startActivity(intent)

                            // We need to trigger MPesa Payment
                            // 1. Phone -> Member Profile
                            // 2. Amount -> cart total cost
                            // 3. Invoice No-> checkout

                            // 1. Phone
                            val phone = PrefsHelper.getPrefs(applicationContext, "phone")
                            val updatedPhone = removePlus(phone, "+")
                            Log.d("updated", updatedPhone)
                            //2.Amount

                            val cartHelper = SQLiteCartHelper(applicationContext)
                            val cartAmount = cartHelper.totalCost()
                            Log.d("amount", cartAmount.toString())

                            //3.Invoice no
                            Log.d("invoice", invoice_no)

                            val api2 = Constants.BASE_URL +"/make_payments"
                            val payload2 = JSONObject()
                            payload2.put("phone",updatedPhone)
                            payload2.put("amount",cartAmount)
                            payload2.put("invoice_no",invoice_no)

                            val apiHelper2 = ApiHelper(applicationContext)
                            apiHelper2.post(api2,payload2,object :ApiHelper.CallBack{
                                override fun onSuccess(result: JSONArray?) {
                                    TODO("Not yet implemented")
                                }

                                override fun onSuccess(result: JSONObject?) {
                                    Toast.makeText(applicationContext, "$result", Toast.LENGTH_SHORT).show()
                                }

                                override fun onFailure(result: String?) {
                                    Toast.makeText(applicationContext, "$result", Toast.LENGTH_SHORT).show()
                                }
                            })

                        }

                        override fun onFailure(result: String?) {
                            Toast.makeText(applicationContext, "$result", Toast.LENGTH_SHORT).show()
                        }
                    })
                }

                //inside login


            }

        }

    }// end onCreate()

    fun generateRandomString(length: Int): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9') // Include lowercase letters, uppercase letters, and numbers
        return (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

}