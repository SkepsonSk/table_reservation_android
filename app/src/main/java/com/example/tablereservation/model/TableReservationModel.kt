package com.example.tablereservation.model

data class TableReservationModel(
    val table_id: Int,
    val date: String,
    val email: String,
    val hour: String
)
