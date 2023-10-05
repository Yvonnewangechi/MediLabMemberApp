package com.modcom.medilabmemberapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modcom.medilabmemberapp.adapters.LabTestCartAdapter
import com.modcom.medilabmemberapp.helpers.PrefsHelper
import com.modcom.medilabmemberapp.helpers.SQLiteCartHelper

class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        //1. We cannot show checkBtn when total cost is 0.0
        val cartHelper = SQLiteCartHelper(requireContext())
        val checkoutBtn : Button = view.findViewById(R.id.checkout)

        if (cartHelper.totalCost() == 0.0){
            checkoutBtn.visibility = View.GONE
        }

        //2. Check cannot be proceeded if user is not logged in(token is Empty)
        checkoutBtn.setOnClickListener {
            val token = PrefsHelper.getPrefs(requireContext(), "refresh_token")
            if(token.isEmpty()){
                Toast.makeText(requireContext(), "Not Logged In", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            else{
                Toast.makeText(requireContext(), "Proceeding to Check Out", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(),CheckOutStep1::class.java)
                startActivity(intent)
            }

        } // end listener

        //3. Find the total Tv, and bind with the totalCost() from the SQLite class
        val total : TextView = view.findViewById(R.id.total)
        total.text = "Total: " + cartHelper.totalCost()

        //4. Find the Recyler:
        val recycler: RecyclerView = view.findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        if(cartHelper.getNumItems() == 0){
            Toast.makeText(requireContext(), "Your Cart is Empty", Toast.LENGTH_SHORT).show()
        }

        else{
            val adapter = LabTestCartAdapter(requireContext())
            adapter.setListItems(cartHelper.getAllItems())
            recycler.adapter = adapter
        }
        return view
    }


}