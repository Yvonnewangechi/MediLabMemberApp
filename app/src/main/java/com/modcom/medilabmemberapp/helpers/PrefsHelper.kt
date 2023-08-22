package com.modcom.medilabmemberapp.helpers

import android.content.Context
import android.content.SharedPreferences

class PrefsHelper {
    companion object{
        // saving data to preferences
        fun savePrefs(context: Context, key: String, value:String){
            val prefs: SharedPreferences = context.getSharedPreferences("store",
                Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString(key, value)
            editor.apply()
        } // end savePrefs

        // getting data from a preference
        fun getPrefs(context:Context, key:String) : String{
            val prefs: SharedPreferences = context.getSharedPreferences("store",
                Context.MODE_PRIVATE)
            val value = prefs.getString(key, "")
            return value.toString()
        } // end getPrefs

        // clearing preferences based on key(one record)
        fun clearPrefsByKey(context: Context, key:String){
            val prefs: SharedPreferences = context.getSharedPreferences("store",
                Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.remove(key)
            editor.apply()
        }// end clearPrefsByKey


        // clearing all the data in the preference
        fun clearPrefs(context: Context){
            val prefs: SharedPreferences = context.getSharedPreferences("store",
                Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.clear()
            editor.apply()
        }


    }// end companion object
} // end PrefsHelper