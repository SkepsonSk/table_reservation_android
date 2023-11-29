package com.example.tablereservation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tablereservation.api.ReservationService
import com.example.tablereservation.ui.theme.TableReservationTheme
import com.example.tablereservation.view.ReservationCreator
import com.example.tablereservation.view.ReservationList
import com.example.tablereservation.viewModel.ReservationViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    private val reservationService = ReservationService("http://54.37.136.163:8080")
    private val reservationViewModel = ReservationViewModel(reservationService = reservationService)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navigationController = rememberNavController()

            TableReservationTheme {
                NavHost(
                    navController = navigationController,
                    startDestination = RESERVATION_LIST_NAV
                ){
                    composable(RESERVATION_LIST_NAV) { ReservationList(
                        reservationService = reservationService,
                        reservationViewModel = reservationViewModel,
                        navigationController = navigationController
                    ).ReservationList() }

                    composable(
                        RESERVATION_CREATOR_NAV,
                        arguments = listOf(
                            navArgument("tableId") { defaultValue = -1 },
                            navArgument("date") { defaultValue = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) }
                        )
                    ) { backStackEntry ->
                        ReservationCreator(
                            reservationService = reservationService,
                            navigationController = navigationController
                        ).ReservationCreator(
                            tableId = backStackEntry.arguments?.getInt("tableId"),
                            date = backStackEntry.arguments?.getString("date")
                        )
                    }
                }
            }
        }
    }
}