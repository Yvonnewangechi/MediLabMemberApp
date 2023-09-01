package com.modcom.medilabmemberapp.models

data class LabTest(
    val availability: String,
    val lab_id: Int,
    val more_info: String,
    val reg_date: String,
    val test_cost: Int,
    val test_description: String,
    val test_discount: Int,
    val test_id: Int,
    val test_name: String
)
