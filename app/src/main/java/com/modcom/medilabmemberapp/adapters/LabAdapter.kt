package com.modcom.medilabmemberapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.modcom.medilabmemberapp.LabTestsActivity
import com.modcom.medilabmemberapp.R
import com.modcom.medilabmemberapp.helpers.PrefsHelper
import com.modcom.medilabmemberapp.models.Lab

class LabAdapter(var context: Context) :
    RecyclerView.Adapter<LabAdapter.ViewHolder>(){

        // declare a variable of type List<Model> (Empty)
        var itemList : List<Lab> = listOf() // its empty

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // Adapter classes implements 3 methods
    // 1. onCreateViewHolder -> Used to call the single item file
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // access/inflate the single_lab.xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_lab,
            parent, false)
        return ViewHolder(view)
    }

    // 2. onBindViewHolder -> Used to bind(attach) data to the view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // find the views from single_lab.xml and bind data from the model
        val tvLabName : TextView = holder.itemView.findViewById(R.id.tvLabName)
        val tvLabPermit: TextView = holder.itemView.findViewById(R.id.tvLabPermit)
        val tvLabPhone: TextView = holder.itemView.findViewById(R.id.tvLabPhone)

        // Assume single lab from the list of labs(itemList)
        val singleLab = itemList[position]

        // bind data to the singleLab
        tvLabName.text = singleLab.lab_name
        tvLabPermit.text = singleLab.permit_id
        tvLabPhone.text = "Tel: " + singleLab.phone

        holder.itemView.setOnClickListener {
            val lab_id = singleLab.lab_id
            PrefsHelper.savePrefs(context, "lab_id", lab_id.toString())
            val intent = Intent(context, LabTestsActivity::class.java)
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
    fun filterList(filterList: List<Lab>){
        itemList = filterList
        notifyDataSetChanged()
    }

    // Earlier the itemList is empty
    // The function will get data from the API and pass to the ItemList

    fun setListItems(data: List<Lab>){

        itemList = data
        notifyDataSetChanged()
    }


}