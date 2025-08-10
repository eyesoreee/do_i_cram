package com.example.doicram.grade_calculator

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doicram.PageHeader
import com.example.doicram.courses.course_list.TabItem
import com.example.doicram.dashboard.OverviewCard

@Composable
fun GradeCalculatorScreen(
    modifier: Modifier = Modifier,
    viewModel: GradeCalculatorViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = remember {
        listOf(
            TabItem("CGPA Calculator") { selectedTabIndex = 0 },
            TabItem("GPA Calculator") { selectedTabIndex = 1 }
        )
    }
    var showAddCourseDialog by remember { mutableStateOf(false) }

    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            PageHeader(
                title = "Grade Calculator",
                subtitle = "Calculate your grade"
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        height = 3.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            ) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = tab.onClick,
                        text = {
                            Text(
                                text = tab.title,
                                fontWeight = if (selectedTabIndex == index) FontWeight.SemiBold else FontWeight.Normal
                            )
                        },
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

        }

        when (selectedTabIndex) {
            0 -> {
                item {
                    OverviewCard(
                        title = "Current CGPA",
                        icon = Icons.AutoMirrored.Filled.TrendingUp,
                        iconColor = MaterialTheme.colorScheme.primary,
                        mainValue = "${state.cgpa ?: "???"}",
                        mainValueColor = MaterialTheme.colorScheme.primary,
                        secondaryValue = "/1.00",
                        bottomIcon = Icons.Default.ArrowUpward,
                        modifier = Modifier.heightIn(max = 145.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OverviewCard(
                        title = "Courses",
                        icon = Icons.AutoMirrored.Filled.MenuBook,
                        iconColor = Color(0xFF4CAF50),
                        mainValue = "${state.totalCourses}",
                        mainValueColor = Color(0xFF4CAF50),
                        secondaryValue = "courses",
                        bottomIcon = Icons.Default.Star,
                        bottomText = "${state.totalUnits} total units",
                        modifier = Modifier.heightIn(max = 145.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = "Course Grades",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Track your academic progress",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }

                        OutlinedButton(
                            onClick = { showAddCourseDialog = true },
                            modifier = Modifier
                                .heightIn(min = 40.dp),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(
                                width = 1.5.dp,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Add Course",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }

                item {
                    CourseBreakdown(
                        courses = state.courses,
                        onDelete = { course ->
                            Log.d("GradeCalculatorScreen", "onDelete: $course")
                            viewModel.onAction(
                                GradeCalculatorAction.OnDeleteCourseClick(course)
                            )
                        },
                        onAddCourseClick = { showAddCourseDialog = true }
                    )
                }
            }
        }
    }

    if (showAddCourseDialog) {
        CalculatorAddCourseDialog(
            onDismiss = { showAddCourseDialog = false },
            onConfirm = { course ->
                viewModel.onAction(GradeCalculatorAction.OnAddCourseClick(course))
            }
        )
    }
}