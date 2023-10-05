package com.modcom.medilabmemberapp.models

data class Dependant(val dependant_id :Int,
                     val surname:String,
                     val dob:String,
                     val others :String,
                     val member_id :Int,
                      val reg_date:String)
