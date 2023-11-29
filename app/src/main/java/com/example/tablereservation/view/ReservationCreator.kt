package com.example.tablereservation.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tablereservation.RESERVATION_LIST_NAV
import com.example.tablereservation.api.ReservationService
import com.example.tablereservation.model.TableReservationModel
import com.example.tablereservation.viewModel.ReservationViewModel

class ReservationCreator(
    reservationService: ReservationService,
    navigationController: NavController
) {

    private val reservationService: ReservationService
    private val navigationController: NavController

    init {
        this.reservationService = reservationService
        this.navigationController = navigationController
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun reserveTable(context: Context, tableId: Int, hour: Int, email: String, date: String) {

        val tableReservationModel = TableReservationModel(
            table_id = tableId,
            hour = hour.toString(),
            email = email,
            date = date
        )

        this.reservationService.reserveTable(tableReservationModel = tableReservationModel) {

            if (it) {
                this.navigationController.navigate(RESERVATION_LIST_NAV)
            } else {
                Toast.makeText(context, "Reservation failed!", Toast.LENGTH_LONG).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("NotConstructor")
    @Composable
    fun ReservationCreator(tableId: Int?, date: String?) {
        val context = LocalContext.current

        var email by remember { mutableStateOf("") }
        var selectedHour = -1

        if (date != null && tableId != null) {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = date,
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    fontSize = 26.sp,
                )
                Text(
                    text = "Reserving table",
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    fontSize = 24.sp,
                )
                TextField(
                    value = email,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onValueChange = { email = it },
                    label = {
                        Text("Email Address")
                    }
                )

                Surface(modifier = Modifier.padding(16.dp)) {
                    ReservationHours().ReservationHours { selectedHour = it }
                }

                Spacer(
                    modifier = Modifier.weight(1.0f)
                )

                Button(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    onClick = {
                        reserveTable(
                            context = context,
                            tableId = tableId,
                            email = email,
                            hour = selectedHour,
                            date = date
                        )
                    }
                ) {
                    Text(
                        fontSize = 20.sp,
                        text = "Reserve"
                    )
                }
            }
        }
    }
}