package com.modcom.medilabmemberapp.helpers

import android.content.Context

class CheckOnBoardingClicked(var context: Context) {


        fun checkOnBardingClicked() : Boolean{
            val screen1Prefs = PrefsHelper.getPrefs(context,"screen1Clicked")
            val screen2Prefs = PrefsHelper.getPrefs(context,"screen2Clicked")

            return screen1Prefs.isNotEmpty() &&screen2Prefs.isNotEmpty()
        }

}