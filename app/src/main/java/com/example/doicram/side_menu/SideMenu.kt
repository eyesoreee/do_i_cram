package com.example.doicram.side_menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.doicram.courses.CoursesScreen
import com.example.doicram.dashboard.DashboardScreen
import com.example.doicram.gpa_calculator.GpaCalculatorScreen
import com.example.doicram.grade_analytics.GradeAnalyticsScreen
import com.example.doicram.settings.SettingsScreen
import com.example.doicram.what_ifs.WhatIfScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SideMenu(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navigationItems = remember {
        listOf(
            NavigationItem(
                icon = Icons.Outlined.Home,
                title = "Dashboard",
                subtitle = "Overview of all courses",
                route = "dashboard"
            ),
            NavigationItem(
                icon = Icons.AutoMirrored.Outlined.MenuBook,
                title = "Courses",
                subtitle = "Manage your subjects",
                route = "courses"
            ),
            NavigationItem(
                icon = Icons.Outlined.Calculate,
                title = "What-If Calculator",
                subtitle = "Calculate required scores",
                route = "whatIf"
            ),
            NavigationItem(
                icon = Icons.Outlined.Grade,
                title = "GPA Calculator",
                subtitle = "Calculate overall GPA",
                route = "gpaCalculator"
            ),
            NavigationItem(
                icon = Icons.Outlined.BarChart,
                title = "Grade Analytics",
                subtitle = "Detailed grade analysis",
                route = "analytics"
            )
        )
    }

    val settingsItem = remember {
        NavigationItem(
            icon = Icons.Outlined.Settings,
            title = "Settings",
            subtitle = "App preferences",
            route = "settings"
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentItem = remember(currentRoute) {
        navigationItems.find { it.route == currentRoute }
            ?: settingsItem.takeIf { it.route == currentRoute }
            ?: navigationItems[0]
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawerContent(
                navigationItems = navigationItems,
                settingsItem = settingsItem,
                currentItem = currentItem,
                navController = navController,
                onItemClick = {
                    scope.launch { drawerState.close() }
                }
            )
        },
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.School,
                                contentDescription = null,
                            )
                            Text("Do I Cram?")
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Open navigation drawer"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = onThemeToggle) {
                            Icon(
                                imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = if (isDarkTheme) "Switch to light mode" else "Switch to dark mode",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                )
            },
        ) { contentPadding ->
            NavHost(
                navController = navController,
                startDestination = "courses",
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(vertical = 16.dp, horizontal = 24.dp)
            ) {
                composable(route = "dashboard") { DashboardScreen() }
                composable(route = "courses") { CoursesScreen() }
                composable(route = "whatIf") { WhatIfScreen() }
                composable(route = "gpaCalculator") { GpaCalculatorScreen() }
                composable(route = "analytics") { GradeAnalyticsScreen() }
                composable(route = "settings") { SettingsScreen() }
            }
        }
    }
}