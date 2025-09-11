package com.example.compose.ui.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.compose.R
import com.example.compose.ui.components.ProBadgeView
import com.example.compose.ui.components.SlideableCell
import com.example.compose.ui.setting.viewModel.SettingViewModel
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.LocalExtendedColors

@Composable
fun SettingScreen(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    viewModel: SettingViewModel = hiltViewModel()
) {
    val onPrimary = MaterialTheme.colorScheme.onPrimary

    LaunchedEffect(Unit) {
        viewModel.addSettingList(
            listOf(
                SettingViewModel.SettingScreenModel.Title("Company settings"),
                SettingViewModel.SettingScreenModel.Item(
                    "leave types",
                    "leave_types",
                    onPrimary
                ),
                SettingViewModel.SettingScreenModel.Item(
                    "leave types",
                    "leave_types2",
                    onPrimary
                )
            )
        )
    }

    SettingScreenView(viewModel, paddingValues)
}

@Composable
private fun SettingScreenView(
    viewModel: SettingViewModel,
    paddingValues: PaddingValues
) {
    val list by viewModel.list.collectAsStateWithLifecycle()
    var openedItemId by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        contentPadding = paddingValues,
    ) {
        item {
            CompanyHeaderView()
        }
        items(items = list) {
            when (it) {
                is SettingViewModel.SettingScreenModel.Title -> SettingTitleView(it.title)
                is SettingViewModel.SettingScreenModel.Item -> {
                    val isRevealed = openedItemId == it.screen
                    SlideableCell(
                        isRevealed = isRevealed,
                        action = {
                            Icon(
                                imageVector = Icons.Default.Share,
                                modifier = Modifier
                                    .background(Color.Blue)
                                    .clickable{
                                        if (openedItemId == it.screen) openedItemId = null
                                    }
                                    .padding(5.dp),
                                tint = Color.White,
                                contentDescription = null,
                            )

                            Icon(
                                imageVector = Icons.Default.Delete,
                                modifier = Modifier
                                    .background(Color.Red)
                                    .clickable{
                                        if (openedItemId == it.screen) openedItemId = null
                                    }
                                    .padding(5.dp),
                                tint = Color.White,
                                contentDescription = null,
                            )

                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                modifier = Modifier
                                    .background(Color.Black)
                                    .clickable{
                                        if (openedItemId == it.screen) openedItemId = null
                                    }
                                    .padding(5.dp),
                                tint = Color.White,
                                contentDescription = null,
                            )

                        },
                        onExpand = {
                            openedItemId = it.screen // 👈 Mark this item as opened
                        },
                        onCollapsed = {
                            if (openedItemId == it.screen) openedItemId = null
                        }
                    ) {
                        SettingItemView(
                            Modifier,
                            it.title,
                            it.screen,
                            it.isPro
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SettingItemView(
    modifier: Modifier = Modifier,
    title: String,
    screen: String,
    isPro: Boolean
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(
                    bounded = true,
                    color = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    // Handle click

                }
            )
            .padding(vertical = 8.dp, horizontal = 20.dp)
    ) {
        Icon(
            imageVector = getSettingIcon(screen),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            title, modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Normal
            )
        )
        if (isPro) ProBadgeView()
    }
}

@Composable
private fun getSettingIcon(screen: String): ImageVector {
    return when (screen) {
        "leave_types" -> ImageVector.vectorResource(id = R.drawable.ic_calendar)
        "leave_types2" -> ImageVector.vectorResource(id = R.drawable.ic_calendar)
        else -> throw IllegalArgumentException("Unknown screen: $screen")
    }
}

@Composable
fun SettingTitleView(title: String) {
    Text(
        title, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 20.dp),
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = LocalExtendedColors.current.titleColor
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    ComposeTheme {
        SettingScreen(paddingValues = PaddingValues())
    }
}