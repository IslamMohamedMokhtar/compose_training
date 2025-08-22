package com.example.compose.ui.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.R
import com.example.compose.ui.calender.CalendarScreen
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.LocalExtendedColors
import com.example.compose.ui.util.NavigationEnum

@Composable
fun DashBoard() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationEnum.CALENDAR.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationEnum.CALENDAR.route) { CalendarScreen() }
//            composable("search") { SearchScreen() }
//            composable("profile") { ProfileScreen() }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    ComposeTheme {
        val items = listOf(
            BottomNavItem(
                stringResource(id = R.string.navigation_calendar),
                NavigationEnum.CALENDAR.route,
                ImageVector.vectorResource(id = R.drawable.ic_calendar)
            ),
            BottomNavItem(
                "Balance",
                NavigationEnum.BALANCE.route,
                ImageVector.vectorResource(id = R.drawable.ic_balance)
            ),
            BottomNavItem(
                "Employees",
                NavigationEnum.EMPLOYEE.route,
                ImageVector.vectorResource(id = R.drawable.ic_people)
            ),
            BottomNavItem(
                "Setting",
                NavigationEnum.SETTINGS.route,
                ImageVector.vectorResource(id = R.drawable.ic_setting)
            ),
        )
        val colorScheme = LocalExtendedColors.current

        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.drawBehind {
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = colorScheme.colorF7F8FA,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = strokeWidth
                )
            }
        ) {
            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route

            items.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(item.icon, contentDescription = item.label)
                    },
                    label = { Text(item.label) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedIconColor = MaterialTheme.colorScheme.secondary,
                        unselectedTextColor = MaterialTheme.colorScheme.secondary,
                    )

                )
            }
        }
    }
}

data class BottomNavItem(val label: String, val route: String, val icon: ImageVector)


@Preview(showBackground = true)
@Composable
fun PreviewDashBoard() {
    ComposeTheme {
        DashBoard()
    }
}

@Preview(showBackground = true, device = "id:pixel_tablet")
@Composable
fun PreviewDashBoardTablet() {
    ComposeTheme {
        DashBoard()
    }
}