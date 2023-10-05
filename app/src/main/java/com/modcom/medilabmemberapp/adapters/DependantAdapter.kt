package com.modcom.medilabmemberapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.modcom.medilabmemberapp.CheckOutStep2
import com.modcom.medilabmemberapp.LabTestsActivity
import com.modcom.medilabmemberapp.R
import com.modcom.medilabmemberapp.helpers.PrefsHelper
import com.modcom.medilabmemberapp.models.Dependant
import com.modcom.medilabmemberapp.models.Lab

class DependantAdapter(var context: Context) :
    RecyclerView.Adapter<DependantAdapter.ViewHolder>(){

    // declare a variable of type List<Model> (Empty)
    var itemList : List<Dependant> = listOf() // its empty

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // Adapter classes implements 3 methods
    // 1. onCreateViewHolder -> Used to call the single item file
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // access/inflate the single_lab.xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_dependant,
            parent, false)
        return ViewHolder(view)
    }

    // 2. onBindViewHolder -> Used to bind(attach) data to the view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // find the views from single_lab.xml and bind data from the model
        val tvSurname : TextView = holder.itemView.findViewById(R.id.tvSurname)
        val tvDob: TextView = holder.itemView.findViewById(R.id.tvDob)
        val tvRegDate: TextView = holder.itemView.findViewById(R.id.tvRegDate)

        // Assume single lab from the list of labs(itemList)
        val singleDependant = itemList[position]

        // bind data to the singleLab
        tvSurname.text = singleDependant.surname + " " + singleDependant.others
        tvDob.text = singleDependant.dob
        tvRegDate.text = ""

        holder.itemView.setOnClickListener {
            val dependant_id = singleDependant.dependant_id
            PrefsHelper.savePrefs(context, "dependant_id", dependant_id.toString())
            val intent = Intent(context, CheckOutStep2::class.java)
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
    fun filterList(filterList: List<Dependant>){
        itemList = filterList
        notifyDataSetChanged()
    }

    // Earlier the itemList is empty
    // The function will get data from the API and pass to the ItemList

    fun setListItems(data: List<Dependant>){
        itemList = data
        notifyDataSetChanged()
    }

}









