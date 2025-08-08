package com.example.doicram.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.doicram.PageHeader

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        PageHeader(
            title = "Dashboard",
            subtitle = "Welcome back! Here's an overview of your academic progress."
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Current GPA Card
        OverviewCard(
            title = "Current CGPA",
            icon = Icons.AutoMirrored.Filled.TrendingUp,
            iconColor = MaterialTheme.colorScheme.primary,
            mainValue = "${state.cgpa ?: "???"}",
            mainValueColor = MaterialTheme.colorScheme.primary,
            secondaryValue = "/1.00",
            bottomIcon = Icons.Default.ArrowUpward,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Active Courses Card
        OverviewCard(
            title = "Active Courses",
            icon = Icons.AutoMirrored.Filled.MenuBook,
            iconColor = Color(0xFF4CAF50),
            mainValue = "${state.activeCourses?.count ?: 0}",
            mainValueColor = Color(0xFF4CAF50),
            secondaryValue = "courses",
            bottomIcon = Icons.Default.Star,
            bottomText = "${state.activeCourses?.totalUnits ?: 0} total units",
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Upcoming Due Card
        OverviewCard(
            title = "Pending Assignments",
            icon = Icons.Default.Schedule,
            iconColor = Color(0xFFFF9800),
            mainValue = "${state.pendingAssignments?.totalCount ?: 0}",
            mainValueColor = Color(0xFFFF9800),
            secondaryValue = "assignments",
            bottomIcon = Icons.Default.Alarm,
            bottomText = "${state.pendingAssignments?.completed} completed",
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Need Attention Card
        OverviewCard(
            title = "Need Attention",
            icon = Icons.Default.Warning,
            iconColor = MaterialTheme.colorScheme.error,
            mainValue = "${state.needAttention?.count ?: 0}",
            mainValueColor = MaterialTheme.colorScheme.error,
            secondaryValue = "courses",
            bottomIcon = Icons.Default.ArrowDownward,
            bottomText = state.needAttention?.subtext,
            modifier = Modifier.weight(1f)
        )
    }
}