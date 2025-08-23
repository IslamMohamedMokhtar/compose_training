package com.example.compose.ui.components

import android.graphics.drawable.GradientDrawable
import android.text.style.ForegroundColorSpan
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.Calendar
import java.util.Date

@Composable
fun CustomCalendarView(
    modifier: Modifier = Modifier,
    initialSelectedDate: Date,
    onDateSelected: (year: Int, month: Int, dayOfMonth: Int) -> Unit,
    onMonthChanged: (year: Int, month: Int) -> Unit = { _, _ -> }
) {
    val cal = Calendar.getInstance().apply { time = initialSelectedDate }
    val primaryColor = MaterialTheme.colorScheme.primary.toArgb()
    val background = MaterialTheme.colorScheme.background.toArgb()
    val onPrimary = MaterialTheme.colorScheme.onPrimary.toArgb()
    val semiTransparentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f).toArgb()

    var selectedDate by remember {
        mutableStateOf(
            CalendarDay.from(
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH)
            )
        )
    }

    var visibleMonth by remember { mutableIntStateOf(cal.get(Calendar.MONTH) + 1) }
    var visibleYear by remember { mutableIntStateOf(cal.get(Calendar.YEAR)) }
    val month = visibleMonth
    val year = visibleYear
    AndroidView(
        modifier = modifier,
        factory = { context ->
            MaterialCalendarView(context).apply {
                setTopbarVisible(false)

                addDecorator(
                    RoundedRectDecorator(
                        selectedDate,
                        primaryColor,
                        background
                    )
                )

                setCurrentDate(selectedDate)
                setSelectedDate(selectedDate)
                showOtherDates = MaterialCalendarView.SHOW_OTHER_MONTHS
                setOnDateChangedListener { widget, date, selected ->
                    if (selected) {
                        selectedDate = date
                        onDateSelected(date.year, date.month + 1, date.day)
                    }
                }
                selectionColor = primaryColor
                tileWidth = ViewGroup.LayoutParams.MATCH_PARENT
                setOnMonthChangedListener { widget, date ->
                    visibleMonth = date.month
                    visibleYear = date.year
                    onMonthChanged(date.year, date.month + 1)
                }
            }
        },
        update = { calendarView ->
            calendarView.removeDecorators()


            calendarView.addDecorator(object : DayViewDecorator {
                override fun shouldDecorate(day: CalendarDay?): Boolean {
                    return true
                }

                override fun decorate(view: DayViewFacade?) {
                    view?.addSpan(ForegroundColorSpan(onPrimary))
                }
            })
            calendarView.addDecorator(object : DayViewDecorator {
                override fun shouldDecorate(day: CalendarDay?): Boolean {
                    day ?: return false
                    return !(day.month == month && day.year == year)
                }

                override fun decorate(view: DayViewFacade?) {
                    view?.addSpan(ForegroundColorSpan(semiTransparentColor))
                }
            })
//            calendarView.addDecorator(EventDotDecorator(eventDates, android.graphics.Color.BLUE))
            calendarView.addDecorator(RoundedRectDecorator(selectedDate, primaryColor, background))
        },
    )

}

class RoundedRectDecorator(
    private val date: CalendarDay,
    private val backgroundColor: Int,
    private val textColor: Int
) : DayViewDecorator {
    private val background = GradientDrawable().apply {
        setColor(backgroundColor)
        cornerRadius = 20f
        setSize(12, 12)
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day == date
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(background)
        view?.addSpan(ForegroundColorSpan(textColor))
    }
}

@Preview
@Composable
fun CalendarViewPreview() {
    CustomCalendarView(onDateSelected = { year, month, dayOfMonth ->
        // Handle date selection here
    }, onMonthChanged = { year, month -> }, initialSelectedDate = Date())
}