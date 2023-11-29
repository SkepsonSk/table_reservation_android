package com.example.tablereservation.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tablereservation.api.ReservationService
import com.example.tablereservation.model.TableModel
import com.example.tablereservation.viewModel.ReservationViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NotConstructor")
class ReservationListing(
    reservationService: ReservationService,
    reservationViewModel: ReservationViewModel,
    navigationController: NavController
) {

    private val reservationService: ReservationService
    private val reservationViewModel: ReservationViewModel
    private val navigationController: NavController

    init {
        this.reservationService = reservationService;
        this.reservationViewModel = reservationViewModel;
        this.navigationController = navigationController;
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun ReservationListing(date: LocalDate) {
        val tableList by reservationViewModel.tableList
        reservationViewModel.getAvailableTables(date)

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Reservations",
                fontSize = 30.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            LazyColumn {
                items(tableList) { table ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp),
                        onClick = {
                            val tableId = table.id
                            val formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                            navigationController.navigate("reservationCreator/$tableId/$formattedDate")
                        }
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                text = "Available Table",
                                modifier = Modifier.padding(bottom = 6.dp),
                                fontSize = 16.sp
                            )
                            Row {
                                Icon(imageVector = Icons.Default.Person, contentDescription = "Seats Count")
                                Text(text = "x${table.seats}")
                            }
                        }
                    }
                }
            }
        }
    }
}