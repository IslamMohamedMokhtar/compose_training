package com.example.compose.ui.dashboard

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.ui.components.CalendarViewWithActionBar
import com.example.compose.ui.components.RequestCard
import com.example.compose.ui.theme.ComposeTheme

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
        modifier = modifier,
        contentPadding = paddings,

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
fun PreviewDashBoard() {
    ComposeTheme {
        Scaffold { innerPadding ->
            DashBoard(paddings = PaddingValues(), modifier = Modifier.padding(innerPadding))
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_tablet")
@Composable
fun PreviewDashBoardTablet() {
    ComposeTheme {
        Scaffold { innerPadding ->
            DashBoard(paddings = PaddingValues(), modifier = Modifier.padding(innerPadding))
        }
    }
}