package com.example.compose.ui.calender

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.ui.components.CalendarViewWithActionBar
import com.example.compose.ui.components.RequestCard

@Composable
fun CalendarScreen(modifier: Modifier = Modifier){
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
    val listModifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
    LazyColumn(
        modifier = modifier
    ) {
        item {
            CalendarViewWithActionBar()
        }
        items(itemsList) { item ->
            RequestCard(modifier = listModifier)
        }

    }
}
@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    CalendarScreen()
}