package com.modcom.medilabmemberapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.modcom.medilabmemberapp.R
import com.modcom.medilabmemberapp.SingleLabTest
import com.modcom.medilabmemberapp.models.Lab
import com.modcom.medilabmemberapp.models.LabTest

class LabTestAdapter(var context: Context) :
    RecyclerView.Adapter<LabTestAdapter.ViewHolder>(){

    // declare a variable of type List<Model> (Empty)
    var itemList : List<LabTest> = listOf() // its empty

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // Adapter classes implements 3 methods
    // 1. onCreateViewHolder -> Used to call the single item file
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // access/inflate the single_lab.xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_lab_tests,
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

        // Clicking on a LabTest and proceeding to SingleLabTest Activity with data from the model
        holder.itemView.setOnClickListener {

            // save the data for a particular lab test to the prefs
            // intent extras

            val intent = Intent(context, SingleLabTest::class.java)
            intent.putExtra("lab_id",singleLab.lab_id )
            intent.putExtra("test_id", singleLab.test_id)
            intent.putExtra("test_dicount", singleLab.test_discount)
            intent.putExtra("test_cost", singleLab.test_cost)
            intent.putExtra("test_name", singleLab.test_name)
            intent.putExtra("test_description", singleLab.test_description)
            intent.putExtra("availability", singleLab.availability)
            intent.putExtra("more_info", singleLab.more_info)
            intent.putExtra("reg_date", singleLab.reg_date)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
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