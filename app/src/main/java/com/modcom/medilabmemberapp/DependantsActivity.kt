package com.modcom.medilabmemberapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.modcom.medilabmemberapp.adapters.DependantAdapter
import com.modcom.medilabmemberapp.adapters.LabAdapter
import com.modcom.medilabmemberapp.helpers.ApiHelper
import com.modcom.medilabmemberapp.helpers.PrefsHelper
import com.modcom.medilabmemberapp.models.Dependant
import com.modcom.medilabmemberapp.models.Lab
import org.json.JSONArray
import org.json.JSONObject

class DependantsActivity : AppCompatActivity() {

    // Declare the resources to be used without assigning values
    lateinit var itemList : List<Dependant>
    lateinit var recyclerView: RecyclerView
    lateinit var DependantAdapter: DependantAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dependants)

        recyclerView = findViewById(R.id.recyclerView)
        DependantAdapter = DependantAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        // call a function to fetch data
        fetchData()
    } //end Oncreate
    fun fetchData(){

        val token = PrefsHelper.getPrefs(applicationContext, "refresh_token")
        if(token.isEmpty()){
            Toast.makeText(applicationContext, "Not Logged In", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            Toast.makeText(applicationContext, "Logged in", Toast.LENGTH_SHORT).show()
            val member_id = PrefsHelper.getPrefs(applicationContext,"LoggedMember_id")
            // The function fetch data from the API
            val api = com.modcom.medilabmemberapp.constants.Constants.BASE_URL + "/view_dependants"
            val payload = JSONObject()
            payload.put("member_id",member_id)
            val helper = ApiHelper(applicationContext)

            helper.post(api,payload,object :ApiHelper.CallBack{
                override fun onSuccess(result: JSONArray?) {
                    val gson = GsonBuilder().create()
                    itemList = gson.fromJson(result.toString(), Array<Dependant>::class.java).toList()
                    DependantAdapter.setListItems(itemList)
                    recyclerView.adapter = DependantAdapter
                }

                override fun onSuccess(result: JSONObject?) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(result: String?) {
                    Toast.makeText(applicationContext, "$result", Toast.LENGTH_SHORT).show()
                }
            })
        }




    }
}