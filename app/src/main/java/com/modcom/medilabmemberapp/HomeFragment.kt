package com.modcom.medilabmemberapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.modcom.medilabmemberapp.helpers.PrefsHelper


class HomeFragment : Fragment() {

     lateinit var tvWelcome: TextView
     lateinit var btnLoogedIn: Button
     lateinit var tvNotLogged: TextView
     lateinit var btnLogout: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        tvWelcome = view.findViewById(R.id.textWelcome)
        btnLoogedIn = view.findViewById(R.id.btnNotLoggedIn)
        tvNotLogged = view.findViewById(R.id.textNotLogged)
        btnLogout = view.findViewById(R.id.btnLogout)

        updateUI()
        return view
    }

    // function to perform UI update -> Login
    fun updateUI(){
        tvWelcome.visibility = View.GONE
        btnLoogedIn.visibility = View.GONE
        tvNotLogged.visibility = View.GONE
        btnLogout.visibility = View.GONE

        // get refresh_token to confirm the Login
        val token = PrefsHelper.getPrefs(requireContext(), "refresh_token")
        if(token.isEmpty()){
            tvNotLogged.visibility = View.VISIBLE
            btnLoogedIn.visibility = View.VISIBLE
            btnLoogedIn.setOnClickListener {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
        }

        else{
            val surname = PrefsHelper.getPrefs(requireContext(),"LoggedSurname")
            tvWelcome.text = "Welcome $surname"
            tvWelcome.visibility = View.VISIBLE
            btnLogout.visibility = View.VISIBLE
            btnLogout.setOnClickListener {
                PrefsHelper.clearPrefs(requireContext())
                val intent = Intent(requireContext(), HomeFragment::class.java)
                startActivity(intent)
            }
        }
    }


}