package com.example.tablereservation.api

import com.example.tablereservation.model.TableModel
import com.example.tablereservation.model.TableReservationModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ReservationApi {

    @Headers(
        "Accept: application/json"
    )
    @GET("/avaible_tables")
    fun getAvailableTables(@Query("date") date: String): Call<List<TableModel>>

    @Headers(
        "Accept: text/plain"
    )
    @POST("/create_reservation")
    fun reserveTable(@Body tableReservationModel: TableReservationModel): Call<String>
}
