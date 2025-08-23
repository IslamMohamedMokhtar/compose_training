package com.example.compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.LocalExtendedColors

@Composable
fun ProBadgeView(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Color(0xFF5F48D1), shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_crown),
            contentDescription = null,
            modifier = Modifier.size(with(LocalDensity.current) {
                12.sp.toDp()
            }),
            tint = LocalExtendedColors.current.yellow,
        )
        Text(
            "Pro",
            style = MaterialTheme.typography.bodySmall.copy(
                color = LocalExtendedColors.current.textAccent
            ),
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ProBadgeViewPreview() {
    ComposeTheme {
        ProBadgeView()
    }
}