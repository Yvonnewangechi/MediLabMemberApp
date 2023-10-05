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
import com.modcom.medilabmemberapp.models.Bookings
import com.modcom.medilabmemberapp.models.Lab
import com.modcom.medilabmemberapp.models.LabTest

class BookingsAdapter(var context: Context) :
    RecyclerView.Adapter<BookingsAdapter.ViewHolder>(){

    // declare a variable of type List<Model> (Empty)
    var itemList : List<Bookings> = listOf() // its empty

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // Adapter classes implements 3 methods
    // 1. onCreateViewHolder -> Used to call the single item file
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // access/inflate the single_lab.xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_bookings,
            parent, false)
        return ViewHolder(view)
    }

    // 2. onBindViewHolder -> Used to bind(attach) data to the view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // find the views from single_bookings.xml and bind data from the model
        val tvBookedFor : TextView = holder.itemView.findViewById(R.id.tvBookedFor)
        val tvInvoiceNo: TextView = holder.itemView.findViewById(R.id.tvInvoiceNo)
        val tvWheretaken: TextView = holder.itemView.findViewById(R.id.tvWhereTaken)

        // Assume single lab from the list of labs(itemList)
        val singleBookings = itemList[position]

        // bind data to the singleBooking
        tvBookedFor.text = singleBookings.booked_for
        tvInvoiceNo.text = singleBookings.invoice_no
        tvWheretaken.text =  singleBookings.where_taken

        // Clicking on a LabTest and proceeding to SingleLabTest Activity with data from the model
       // holder.itemView.setOnClickListener {
        //
        //        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    // Custom Functions
    // 1. Filter the itemList(contain the list of all Labs)
    // Used for searching.....
    fun filterList(filterList: List<Bookings>){
        itemList = filterList
        notifyDataSetChanged()
    }

    // Earlier the itemList is empty
    // The function will get data from the API and pass to the ItemList

    fun setListItems(data: List<Bookings>){
        itemList = data
        notifyDataSetChanged()
    }


}