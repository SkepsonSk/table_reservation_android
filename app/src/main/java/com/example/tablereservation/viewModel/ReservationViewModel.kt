package com.example.tablereservation.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.tablereservation.api.ReservationService
import com.example.tablereservation.model.TableModel
import java.time.LocalDate

class ReservationViewModel(reservationService: ReservationService) : ViewModel() {

    private val reservationService: ReservationService

    init {
        this.reservationService = reservationService
    }

    val tableList = mutableStateOf<List<TableModel>>(emptyList())

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAvailableTables(date: LocalDate) {
        this.reservationService.getAvailableTables(date = date) { retrievedTableList ->
            tableList.value = retrievedTableList
        }
    }

}