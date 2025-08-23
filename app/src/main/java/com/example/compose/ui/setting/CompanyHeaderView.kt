package com.example.compose.ui.setting

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.example.compose.R
import com.example.compose.ui.components.ProBadgeView
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.LocalExtendedColors
import com.example.compose.ui.util.LocalNavController
import com.example.compose.ui.util.NavigationEnum

@Composable
fun CompanyHeaderView(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(20.dp),
    ) {
        CircleAvatar(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQoFRQjM-wM_nXMA03AGDXgJK3VeX7vtD3ctA&s",
            size = 70.dp
        )
        Spacer(modifier = Modifier.padding(end = 16.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Company name",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 20.sp
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AddLocation,
                    contentDescription = null,
                    modifier = Modifier.size(with(LocalDensity.current) {
                        12.sp.toDp()
                    }),
                    tint = LocalExtendedColors.current.textSecondary
                )
                Text(
                    "Enozom Software",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = LocalExtendedColors.current.textSecondary
                    ),
                    modifier = Modifier.padding(end = 12.dp)
                )
                ProBadgeView()
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AddLocation,
                    contentDescription = null,
                    modifier = Modifier.size(with(LocalDensity.current) {
                        12.sp.toDp()
                    }),
                    tint = LocalExtendedColors.current.textSecondary
                )
                Text(
                    "Egypt",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = LocalExtendedColors.current.textSecondary
                    )
                )
            }
        }
    }
}

@Composable
fun CircleAvatar(
    imageUrl: String,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    borderColor: Brush = Brush.linearGradient(
        colors = listOf(Color(0xFFFD3177), MaterialTheme.colorScheme.primary)
    ),
    borderWidth: Dp = 2.dp,
    gap: Dp = 4.dp
) {
    val navigationController = LocalNavController.current

    Box(
        modifier = modifier
            .size(size)
            .border(borderWidth, borderColor, CircleShape)
            .padding(gap)
            .clip(CircleShape)
            .clickable {
                navigationController.navigate(
                    NavigationEnum.IMAGE_PREVIEW.withArgs(imageUrl)
                )
            }
    ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            loading = {
                ShimmerEffect(modifier = Modifier.fillMaxSize())
            },
            error = {
                Icon(
                    painter = painterResource(id = R.drawable.rounded_rect_selector), // fallback icon
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.fillMaxSize()
                )
            }
        )
    }
}

@Composable
fun ShimmerEffect(modifier: Modifier = Modifier) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.1f),
        Color.LightGray.copy(alpha = 0.3f),
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "shimmer_anim"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnim - 200f, y = 0f),
        end = Offset(x = translateAnim, y = 0f)
    )

    Box(
        modifier = modifier
            .background(brush, CircleShape)
    )
}

@Preview(showBackground = true)
@Composable
fun CompanyHeaderPreview() {
    val fakeNavController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides fakeNavController) {
        ComposeTheme {
            CompanyHeaderView()
        }
    }
}