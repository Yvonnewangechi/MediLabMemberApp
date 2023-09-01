package com.modcom.medilabmemberapp.models

data class Lab(val email: String,
               val lab_id: Int,
               val lab_name: String,
               val password: String,
               val permit_id: String?,
               val phone: String,
               val reg_date: String
               )
