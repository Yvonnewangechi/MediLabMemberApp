package com.modcom.medilabmemberapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.bottom_home -> { replaceFragment(HomeFragment())}
                R.id.bottom_search -> { replaceFragment(SearchFragment())}
                R.id.bottom_add -> { replaceFragment(AddDependantFragment())}
                R.id.bottom_cart -> { replaceFragment(CartFragment())}
                R.id.bottom_profile -> { replaceFragment(ProfileFragment())}

            } // end when
            true

        } // end listener

        replaceFragment(HomeFragment())

    } // end onCreate()

    // function to replace a Fragment based on menuItem
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }
}