package com.example.compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.R

@Composable
fun RequestCard(
    modifier: Modifier = Modifier,
    textColor: Color = Color.Red,
    backgroundColor: List<Color> = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
) {
    val isTablet = booleanResource(id = R.bool.isTablet)

    Box(
        modifier = modifier
            .clickable { /* your onClick logic here */ }
            .height(if (isTablet) 108.dp else 72.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clipToBounds()
            .background(
                brush = Brush.verticalGradient(
                    colors = backgroundColor
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(4.dp)
                .background(textColor)
                .align(Alignment.CenterStart)
                .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)) // round left corners
        )
        Row(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(start = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = null,
                    tint = textColor
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        "Sick Leave",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(fontWeight = FontWeight.Bold, color = textColor)
                    )
                    Text(
                        "Days/Year",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Used/Total",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontWeight = FontWeight.Normal, color = Color(0xFF555555))
                )
                Text(
                    "4/12",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontWeight = FontWeight.Normal),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = null,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable { })
        }
    }
}


@Preview
@Composable
fun RequestCardPreview() {
    RequestCard()
}