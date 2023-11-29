package com.example.tablereservation.api

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.tablereservation.model.TableModel
import com.example.tablereservation.model.TableReservationModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ReservationService(url: String) {

    private var api: ReservationApi? = null;

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        this.api = retrofit.create(ReservationApi::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAvailableTables(date: LocalDate, callback: (List<TableModel>) -> Unit) {
        val formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        this.api?.getAvailableTables(date = formattedDate)?.enqueue(object: Callback<List<TableModel>> {
            override fun onResponse(call: Call<List<TableModel>>, response: Response<List<TableModel>>) {
                if(response.isSuccessful) {
                    response.body()?.let { callback(it) }
                }
            }

            override fun onFailure(call: Call<List<TableModel>>, err: Throwable) {
                Log.e("ReservationService (getAvailableTables)", err.message.toString())
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun reserveTable(tableReservationModel: TableReservationModel, callback: (Boolean) -> Unit) {
        this.api?.reserveTable(tableReservationModel = tableReservationModel)?.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("ReservationService (reserveTable)", response.isSuccessful.toString())
                Log.d("ReservationService (reserveTable)", response.code().toString())

                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<String>, err: Throwable) {
                Log.e("ReservationService (reserveTable)", err.message.toString())
                callback(false)
            }
        })
    }
}