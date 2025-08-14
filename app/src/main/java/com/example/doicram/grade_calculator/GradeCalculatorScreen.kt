package com.example.doicram.grade_calculator

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextOverflow
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
    var showAddAssignmentDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

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

            1 -> {
                item {
                    OverviewCard(
                        title = "Current GPA",
                        icon = Icons.AutoMirrored.Filled.TrendingUp,
                        iconColor = MaterialTheme.colorScheme.primary,
                        mainValue = "${state.gpa ?: "???"}",
                        mainValueColor = MaterialTheme.colorScheme.primary,
                        secondaryValue = "/1.00",
                        bottomIcon = Icons.Default.ArrowUpward,
                        modifier = Modifier.heightIn(max = 145.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Assignment Manager",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            IconButton(
                                onClick = { showEditDialog = showEditDialog.not() }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                )
                            }

                            TextButton(
                                onClick = {
                                    showAddAssignmentDialog = showAddAssignmentDialog.not()
                                },
                                modifier = Modifier.border(
                                    1.dp,
                                    MaterialTheme.colorScheme.inverseSurface,
                                    MaterialTheme.shapes.extraLarge
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.inverseSurface,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "Add Item",
                                    color = MaterialTheme.colorScheme.inverseSurface
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                items(state.categories) { category ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            // Category Header
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Surface(
                                        modifier = Modifier.size(20.dp),
                                        shape = CircleShape,
                                        color = category.color,
                                        shadowElevation = 2.dp
                                    ) {}

                                    Text(
                                        text = category.name,
                                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                ) {
                                    Text(
                                        text = "${category.assignments.size} assignments",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                                        modifier = Modifier
                                            .padding(horizontal = 12.dp, vertical = 6.dp)
                                            .weight(1f)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            if (category.assignments.isEmpty()) {
                                // Empty state
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.Assignment,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                                            modifier = Modifier.size(48.dp)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "No assignments added yet",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                        Text(
                                            text = "Add your first assignment to get started",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                            modifier = Modifier.padding(top = 4.dp)
                                        )
                                    }
                                }
                            } else {
                                // Assignments list
                                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    category.assignments.forEach { assignment ->
                                        GpaAssignmentCard(
                                            assignment = assignment,
                                            categoryColor = MaterialTheme.colorScheme.primary, // TODO: Subject to change
                                            onDeleteClick = { course ->
                                                viewModel.onAction(
                                                    GradeCalculatorAction.OnDeleteAssignmentClick(
                                                        course
                                                    )
                                                )
                                            },
                                            onEditClick = { },
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
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

    if (showAddAssignmentDialog) {
        CalculatorAddAssignmentDialog(
            categories = state.categories,
            onDismissRequest = {
                showAddAssignmentDialog = false
            },
            onAddAssignment = { assignment ->
                viewModel.onAction(GradeCalculatorAction.OnAddAssignmentClick(assignment))
                showAddAssignmentDialog = false
            }
        )
    }

    if (showEditDialog) {
        CalculatorEditDialog(
            onDismissRequest = { showEditDialog = false },
            onEditCourse = { categories, scales ->
                viewModel.onAction(
                    GradeCalculatorAction.OnEditClick(
                        categories,
                        scales
                    )
                )
                showEditDialog = false
            },
            gpaCategories = state.categories,
            gpaScales = state.scales
        )
    }
}