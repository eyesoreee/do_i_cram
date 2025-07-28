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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.doicram.PageHeader

@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        PageHeader(
            title = "Dashboard",
            subtitle = "Welcome back! Here's an overview of your academic progress."
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Current GPA Card
        OverviewCard(
            title = "Current GPA",
            icon = Icons.AutoMirrored.Filled.TrendingUp,
            iconColor = MaterialTheme.colorScheme.primary,
            mainValue = "2.96",
            mainValueColor = MaterialTheme.colorScheme.primary,
            secondaryValue = "/4.00",
            bottomIcon = Icons.Default.ArrowUpward,
            bottomText = "+0.12 from last semester",
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Active Courses Card
        OverviewCard(
            title = "Active Courses",
            icon = Icons.AutoMirrored.Filled.MenuBook,
            iconColor = Color(0xFF4CAF50), // Keep green as it works well in both themes
            mainValue = "2",
            mainValueColor = Color(0xFF4CAF50),
            secondaryValue = "courses",
            bottomIcon = Icons.Default.Star,
            bottomText = "7 total credits",
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Upcoming Due Card
        OverviewCard(
            title = "Upcoming Due",
            icon = Icons.Default.Schedule,
            iconColor = Color(0xFFFF9800), // Keep orange as it works well in both themes
            mainValue = "0",
            mainValueColor = Color(0xFFFF9800),
            secondaryValue = "assignments",
            bottomIcon = Icons.Default.Alarm,
            bottomText = "Next due in 2 days",
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Need Attention Card
        OverviewCard(
            title = "Need Attention",
            icon = Icons.Default.Warning,
            iconColor = MaterialTheme.colorScheme.error,
            mainValue = "2",
            mainValueColor = MaterialTheme.colorScheme.error,
            secondaryValue = "courses",
            bottomIcon = Icons.Default.ArrowDownward,
            bottomText = "Below target grade",
            modifier = Modifier.weight(1f)
        )
    }
}