package com.example.compose.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.ui.theme.Typography
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun CalendarViewWithActionBar(modifier: Modifier = Modifier, initialDate: Date = Date()) {
    var isCollapsed by rememberSaveable { mutableStateOf(false) }
    var selectedDate by rememberSaveable { mutableStateOf(initialDate) }
    val calendar = Calendar.getInstance()
    val targetHeight = if (isCollapsed) 0.dp
    else if (booleanResource(id = R.bool.isTablet)) 312.dp else 250.dp

    val animatedHeight by animateDpAsState(
        targetValue = targetHeight,
        animationSpec = tween(
            durationMillis = 350,
            easing = LinearOutSlowInEasing
        ),
        label = "calendarHeight"
    )
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "August 2023", style = Typography.titleLarge.copy(
                    fontWeight = FontWeight.Medium
                )
            )

            IconButton(
                onClick = { isCollapsed = !isCollapsed },
                modifier = Modifier.size(with(LocalDensity.current) { 20.sp.toDp() } + 8.dp)
            ) {
                Icon(
                    imageVector = if (!isCollapsed) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.size(with(LocalDensity.current) { 20.sp.toDp() })
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { selectedDate = Date() },
                modifier = Modifier.size(with(LocalDensity.current) { 28.sp.toDp() } + 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.FilterAlt,
                    contentDescription = null,
                    modifier = Modifier.size(with(LocalDensity.current) { 20.sp.toDp() })
                )
            }

            IconButton(
                onClick = {

                },
                modifier = Modifier.size(with(LocalDensity.current) { 28.sp.toDp() } + 8.dp)

            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = null,
                    modifier = Modifier.size(with(LocalDensity.current) { 20.sp.toDp() })
                )
            }

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(animatedHeight)
                .clipToBounds()
        ) {
            CustomCalendarView(
                onDateSelected = { y, m, d ->
                    calendar.apply {
                        set(Calendar.YEAR, y)
                        set(Calendar.MONTH, m)
                        set(Calendar.DAY_OF_MONTH, d)
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                    selectedDate = calendar.time
                },
                onMonthChanged = { _, _ -> },
                initialSelectedDate = selectedDate,
                modifier = Modifier.fillMaxWidth().height(targetHeight)
            )
        }
        Text(
            SimpleDateFormat("EEEE, d MMMM", Locale.getDefault()).format(selectedDate),
            style = Typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333)
            ),
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 14.dp)
                .background(color = Color(0xFFF5F6F9))
        )


    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalendarViewWithActionBar() {
    CalendarViewWithActionBar()
}

@Preview(
    showBackground = true, device = "spec:width=2560px,height=1600px,dpi=640"
)
@Composable
fun PreviewCalendarViewWithActionBarTablet() {
    CalendarViewWithActionBar()
}