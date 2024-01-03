package com.modcom.medilabmemberapp

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.modcom.medilabmemberapp.adapters.BookingsAdapter
import com.modcom.medilabmemberapp.helpers.ApiHelper
import com.modcom.medilabmemberapp.helpers.PrefsHelper
import com.modcom.medilabmemberapp.models.Bookings
import org.json.JSONArray
import org.json.JSONObject

class ProfileFragment : androidx.fragment.app.Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var bookingsAdapter: BookingsAdapter
    lateinit var itemList: List<Bookings>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        bookingsAdapter = BookingsAdapter(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        fetchData()

        val btnLogout = view.findViewById<Button>(R.id.logout)
        btnLogout.setOnClickListener {
            PrefsHelper.clearPrefs(requireContext())
            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finishAffinity()
        }

        return view
    }

    fun fetchData() {
        val token = PrefsHelper.getPrefs(requireContext(), "refresh_token")
        if (token.isEmpty()) {
            Toast.makeText(requireContext(), "Not Logged In", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        } else {
            val api = com.modcom.medilabmemberapp.constants.Constants.BASE_URL + "/my_bookings"
            val helper = ApiHelper(requireContext())
            val member_id = PrefsHelper.getPrefs(requireContext(), "LoggedMember_id")
            val payload = JSONObject()
            payload.put("member_id", member_id)
            helper.post(api, payload, object : ApiHelper.CallBack {
                override fun onSuccess(result: JSONArray?) {
                    val gson = GsonBuilder().create()
                    itemList = gson.fromJson(result.toString(), Array<Bookings>::class.java).toList()
                    bookingsAdapter.setListItems(itemList)
                    recyclerView.adapter = bookingsAdapter
                }

                override fun onSuccess(result: JSONObject?) {
                    // Handle JSONObject response if needed
                }

                override fun onFailure(result: String?) {
                    // Handle failure response if needed
                }
            })
        }
    }
}
