package com.example.tablereservation.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tablereservation.api.ReservationService
import com.example.tablereservation.viewModel.ReservationViewModel
import java.time.LocalDate

class ReservationList(
    reservationService: ReservationService,
    reservationViewModel: ReservationViewModel,
    navigationController: NavController
) {

    private var reservationService: ReservationService;
    private var reservationViewModel: ReservationViewModel;
    private var navigationController: NavController

    init {
        this.reservationService = reservationService;
        this.reservationViewModel = reservationViewModel;
        this.navigationController = navigationController;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NotConstructor")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ReservationList() {
        var selectedDate by remember {
            mutableStateOf(LocalDate.now())
        }

        Scaffold(
            topBar = { ReservationTopAppBar().ReservationTopAppBar() }
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ReservationCalendar().ReservationCalendar { selectedDate = it };
                ReservationListing(
                    reservationService = reservationService,
                    reservationViewModel = reservationViewModel,
                    navigationController = navigationController
                ).ReservationListing(selectedDate);
            }
        }
    }

}