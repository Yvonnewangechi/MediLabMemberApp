package com.modcom.medilabmemberapp

import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.modcom.medilabmemberapp.adapters.LabAdapter
import com.modcom.medilabmemberapp.helpers.ApiHelper
import com.modcom.medilabmemberapp.models.Lab
import org.json.JSONArray
import org.json.JSONObject

class SearchFragment : Fragment() {
    // outside
    // Declare the resources to be used without assigning values
    lateinit var itemList : List<Lab>
    lateinit var recyclerView: RecyclerView
    lateinit var labAdapter: LabAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // This acts like the onCreate() function in activities
        // 1. find recyclerView

        val view = inflater.inflate(R.layout.fragment_search, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        labAdapter = LabAdapter(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(context)
        // call a function to fetch data
        fetchData()

        // find the searchView based on Id
        val edSearch : EditText = view.findViewById(R.id.search)
        edSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(textChanged: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filter(textChanged.toString())

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        return view

    }  // end onCreateView()

    fun fetchData(){
        // The function fetch data from the API
        val api = com.modcom.medilabmemberapp.constants.Constants.BASE_URL + "/laboratories"
        val helper = ApiHelper(requireContext())
        helper.get(api, object : ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {
                // result is JSONArray response data
                // We need a library to convert result(JSONArray) to List<Lab>
                // we use gson library
                val gson = GsonBuilder().create()
                itemList = gson.fromJson(result.toString(), Array<Lab>::class.java).toList()
                labAdapter.setListItems(itemList)
                recyclerView.adapter = labAdapter
            }

            override fun onSuccess(result: JSONObject?) {
                TODO("Not yet implemented")
            }

            override fun onFailure(result: String?) {
                Toast.makeText(requireContext(), "${result.toString()}", Toast.LENGTH_SHORT).show()
            }
        })
    }

     fun filter(text:String){
        // create an array list to filter our labs
        val filteredList: ArrayList<Lab> = ArrayList()

        // loop through the itemList
        for (item in itemList){
            if (item.lab_name.lowercase().contains(text.lowercase())){
                filteredList.add(item)
            }
        }

        if (filteredList.isEmpty()){
            labAdapter.filterList(filteredList)
        }

        else{
            labAdapter.filterList(filteredList)
        }

    } // end filter function

}