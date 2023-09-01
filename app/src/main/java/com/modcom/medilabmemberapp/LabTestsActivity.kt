package com.modcom.medilabmemberapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.modcom.medilabmemberapp.adapters.LabAdapter
import com.modcom.medilabmemberapp.adapters.LabTestAdapter
import com.modcom.medilabmemberapp.helpers.ApiHelper
import com.modcom.medilabmemberapp.helpers.PrefsHelper
import com.modcom.medilabmemberapp.models.Lab
import com.modcom.medilabmemberapp.models.LabTest
import org.json.JSONArray
import org.json.JSONObject

class LabTestsActivity : AppCompatActivity() {
    // outside
    // Declare the resources to be used without assigning values
    lateinit var itemList : List<LabTest>
    lateinit var recyclerView: RecyclerView
    lateinit var labTestAdapter: LabTestAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_tests)

        recyclerView = findViewById(R.id.recyclerView)
        labTestAdapter = LabTestAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        // call a function to fetch data
        fetchData()
    }

    fun fetchData(){
        // The function fetch data from the API
        val api = com.modcom.medilabmemberapp.constants.Constants.BASE_URL + "/lab_tests"
        val helper = ApiHelper(applicationContext)
        val body = JSONObject()

        // Static Lab Id Variable, To be Replaced with the Lab Id from Search Fragment
        // Replace with dynamic value from the prefs:
        val lab_id = PrefsHelper.getPrefs(applicationContext, "lab_id")
        body.put("lab_id", lab_id)
        helper.post(api, body, object : ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {
                // Lab Test are returned as JSONArray
                val gson = GsonBuilder().create()
                itemList = gson.fromJson(result.toString(), Array<LabTest>::class.java).toList()
                labTestAdapter.setListItems(itemList)
                recyclerView.adapter = labTestAdapter
            }

            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(applicationContext, "No Lab Test Found", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(result: String?) {
                Toast.makeText(applicationContext, "Error...", Toast.LENGTH_SHORT).show()
            }
        })



    }
}