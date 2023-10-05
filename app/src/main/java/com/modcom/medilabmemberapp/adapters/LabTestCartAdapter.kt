package com.modcom.medilabmemberapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.modcom.medilabmemberapp.R
import com.modcom.medilabmemberapp.SingleLabTest
import com.modcom.medilabmemberapp.helpers.PrefsHelper
import com.modcom.medilabmemberapp.helpers.SQLiteCartHelper
import com.modcom.medilabmemberapp.models.LabTest

class LabTestCartAdapter(var context: Context) :
    RecyclerView.Adapter<LabTestCartAdapter.ViewHolder>(){

    // declare a variable of type List<Model> (Empty)
    var itemList : List<LabTest> = listOf() // its empty

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // Adapter classes implements 3 methods
    // 1. onCreateViewHolder -> Used to call the single item file
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // access/inflate the single_lab.xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_labtests_cart,
            parent, false)
        return ViewHolder(view)
    }

    // 2. onBindViewHolder -> Used to bind(attach) data to the view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // find the views from single_lab.xml and bind data from the model
        val tvLabTestName : TextView = holder.itemView.findViewById(R.id.tvLabTestName)
        val tvLabTestCost: TextView = holder.itemView.findViewById(R.id.tvLabTestCost)
        val tvLabTestAvalability: TextView = holder.itemView.findViewById(R.id.tvLabTestAvailability)

        // Assume single lab from the list of labs(itemList)
        val singleLab = itemList[position]

        // bind data to the singleLab
        tvLabTestName.text = singleLab.test_name
        tvLabTestCost.text = "KSHS "+ singleLab.test_cost.toString()
        tvLabTestAvalability.text = "Availability : " + singleLab.availability

        //Save Lab_id and test_id to the prefs
        val lab_id = singleLab.lab_id
        PrefsHelper.savePrefs(context,"cart_lab_id",lab_id.toString())

        val test_id = singleLab.test_id
        PrefsHelper.savePrefs(context,"cart_test_id",test_id.toString())



        // Click on the Button to remove item from the cart
        val removeBtn : Button = holder.itemView.findViewById(R.id.remove)
        // Assign removeBtn a click listener
        removeBtn.setOnClickListener {
            val test_id = singleLab.test_id
            val cartHelper = SQLiteCartHelper(context)
            cartHelper.clearCartByID(test_id.toString())

        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    // Custom Functions
    // 1. Filter the itemList(contain the list of all Labs)
    // Used for searching.....
    fun filterList(filterList: List<LabTest>){
        itemList = filterList
        notifyDataSetChanged()
    }

    // Earlier the itemList is empty
    // The function will get data from the API and pass to the ItemList

    fun setListItems(data: List<LabTest>){
        itemList = data
        notifyDataSetChanged()
    }

}