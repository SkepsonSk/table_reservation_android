package com.example.tablereservation.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream

class ReservationCalendar {

    data class ReservationDayModel(
        val date: LocalDate,

        val day: String,
        val month: Int,
        val year: Int,

        val weekDayName: String,
        val monthName: String
    )

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAvailableDates(startDate: LocalDate = LocalDate.now()): List<ReservationDayModel> {
        val endDay = startDate.plusDays(30)

        val weekDaysCount = ChronoUnit.DAYS.between(startDate, endDay)
        val weekDays = Stream.iterate(startDate) { weekDay ->
            weekDay.plusDays(1)
        }
            .limit(weekDaysCount)
            .collect(Collectors.toList())

        return weekDays.map { weekDay ->
            ReservationDayModel(
                date = weekDay,
                day = if (weekDay.dayOfMonth > 9) weekDay.dayOfMonth.toString() else "0${weekDay.dayOfMonth}",
                month = weekDay.monthValue,
                year = weekDay.year,
                weekDayName = weekDay.dayOfWeek.name.substring(0, 3),
                monthName = weekDay.month.name.substring(0, 3)
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NotConstructor")
    @Composable
    fun ReservationCalendar(onSelectedDate: (LocalDate) -> Unit) {
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Today",
                fontSize = 30.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(items = getAvailableDates(), itemContent = { dayModel ->
                    ReservationDay(day = dayModel, onSelectedDate)
                })
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ReservationDay(day: ReservationDayModel, onSelectedDate: (LocalDate) -> Unit) {
        Card(
            modifier = Modifier.padding(4.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            onClick = {
                onSelectedDate(day.date)
            }
        ) {
            Column(
                modifier = Modifier.padding(6.dp)
            ) {
                Text(
                    text = day.weekDayName,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(
                        bottom = 12.dp,
                        start = 12.dp,
                        end = 12.dp
                    )
                )

                Text(
                    text = day.day.toString(),
                    fontSize = 32.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
                Text(
                    text = day.monthName,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }
        }
    }

}