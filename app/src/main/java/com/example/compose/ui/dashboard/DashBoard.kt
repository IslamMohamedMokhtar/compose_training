package com.example.compose.ui.dashboard

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.R
import com.example.compose.ui.components.CustomCalendarView
import com.example.compose.ui.components.RequestCard
import com.example.compose.ui.theme.ComposeTheme
import java.util.Date

@Composable
fun DashBoard(modifier: Modifier = Modifier, paddings: PaddingValues) {
    val itemsList = listOf(
        "Apple",
        "Banana",
        "Cherry",
        "Date",
        "Elderberry",
        "Apple",
        "Banana",
        "Cherry",
        "Date",
        "Elderberry",
        "Apple",
        "Banana",
        "Cherry",
        "Date",
        "Elderberry",
        "Apple",
        "Banana",
        "Cherry",
        "Date",
        "Elderberry",
    )
    val listModifier = Modifier.padding(5.dp)
    LazyColumn(
        contentPadding = paddings,

        ) {
        item {
            CustomCalendarView(
                onDateSelected = { year, month, dayOfMonth ->
                    // Handle date selected
                },
                onMonthChanged = { year, month ->
                    // Handle month changed
                },
                initialSelectedDate = Date(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height((if (booleanResource(id = R.bool.isTablet)) 312 else 250).dp)
            )
        }
        items(itemsList) { item ->
            RequestCard(modifier = listModifier)
        }

    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@Composable
fun PreviewDashBoard() {
    ComposeTheme {
        Scaffold { innerPadding ->
            DashBoard(paddings = PaddingValues(), modifier = Modifier.padding(innerPadding))
        }
    }
}