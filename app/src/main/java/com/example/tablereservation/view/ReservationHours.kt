package com.example.tablereservation.view

import android.annotation.SuppressLint
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tablereservation.ui.theme.Purple80
import com.example.tablereservation.ui.theme.PurpleGrey40
import com.example.tablereservation.ui.theme.PurpleGrey80

class ReservationHours {

    data class HourModel(
        val hour: Int,
        val stringHour: String
    )

    private fun getHours(): List<HourModel> {
        val hourList = listOf(12, 13, 14, 15, 16, 17, 18, 19)
        return hourList.map { hour -> HourModel(
            hour = hour,
            stringHour = getHourStringValue(hour = hour)
        ) }
    }

    private fun getHourStringValue(hour: Int): String {
        return if (hour > 12) {
            val hourBritish = hour - 12
            val hourStringValue = if (hourBritish < 10) "0$hourBritish" else hourBritish.toString()
            "${hourStringValue}PM"
        } else {
            val hourStringValue = if (hour < 10) "0$hour" else hour.toString()
            "${hourStringValue}AM"
        }
    }

    @SuppressLint("NotConstructor")
    @Composable
    fun ReservationHours(onHourSelected: (Int) -> Unit) {
        var selectedHour by remember { mutableIntStateOf(-1) }

        Column {
            Text(
                text = "Select an Hour",
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalItemSpacing = 4.dp
            ) {
                items(items = getHours()) { hourModel ->
                    ReservationHour(hourModel = hourModel, selectedHour) {
                        selectedHour = it
                        onHourSelected(it)
                    }
                }
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ReservationHour(hourModel: HourModel, selectedHour: Int, onHourSelected: (Int) -> Unit) {
        Card(
            colors = if (hourModel.hour == selectedHour)
                CardDefaults.cardColors( containerColor = Color.LightGray )
            else CardDefaults.cardColors(),

            onClick = { onHourSelected(hourModel.hour) }
        ) {
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Time",
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(text = hourModel.stringHour)
            }
        }
    }

}
