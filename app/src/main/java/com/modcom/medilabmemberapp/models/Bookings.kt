package com.modcom.medilabmemberapp.models

data class Bookings(
    val appointment_date :String,
    val appointment_time :String,
    val booked_id :Int,
    val booked_for:String,
    val dependant_id :Int,
    val invoice_no:String,
    val lab_id :Int,
    val longitude:String,
    val latitude:String,
    val member_id :Int,
    val status :Int,
    val test_id :Int,
    val where_taken :String

)
