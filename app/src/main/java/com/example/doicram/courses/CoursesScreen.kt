package com.example.doicram.courses

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.doicram.Loading
import com.example.doicram.PageHeader

data class CourseTab(
    val icon: ImageVector,
    val text: String,
    val onClick: () -> Unit,
    val courseGrade: String? = null
)

@Composable
fun CoursesScreen(
    modifier: Modifier = Modifier,
    viewModel: CoursesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var selectedTabIndex by remember { mutableIntStateOf(-1) }
    var showAddCourseDialog by remember { mutableStateOf(false) }

    val courseTabs = remember(state.courses) {
        state.courses.map { course ->
            CourseTab(
                icon = Icons.AutoMirrored.Outlined.MenuBook,
                text = course.course.code,
                courseGrade = course.course.grade?.toString(),
                onClick = {}
            )
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        // First PageHeader
        item {
            PageHeader(
                title = "Courses",
                subtitle = "Manage your courses"
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        // CourseTabList
        item {
            CourseTabList(
                courseTabs = courseTabs,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { index ->
                    if (index >= 0 && index < state.courses.size) {
                        selectedTabIndex = index
                        viewModel.onAction(CoursesAction.SelectCourse(state.courses[index].course.id))
                    } else {
                        selectedTabIndex = -1
                        viewModel.onAction(CoursesAction.DeselectCourse)
                    }
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        when {
            state.isLoading -> {
                item { Loading() }
            }

            state.error != null -> {
                item {
                    ErrorLoadingCourses(
                        state = state,
                        onRefresh = viewModel::loadCourses
                    )
                }
            }

            state.courses.isEmpty() -> {
                item {
                    EmptyCourseList(
                        onClick = { showAddCourseDialog = showAddCourseDialog.not() }
                    )
                }
            }

            state.selectedCourse != null -> {
                item {
                    DetailedCourse(state.selectedCourse!!)
                }
            }

            else -> {
                item {
                    PageHeader(
                        title = "Course Management",
                        subtitle = "Add, edit, and configure your courses and grading categories."
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Row with "All courses" and "Add Course" button
                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "All courses",
                            style = MaterialTheme.typography.titleLarge
                        )
                        TextButton(
                            onClick = { showAddCourseDialog = showAddCourseDialog.not() },
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
                                text = "Add Course",
                                color = MaterialTheme.colorScheme.inverseSurface
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                items(state.courses) { course ->
                    CourseCard(
                        course = course,
                        onEdit = { },
                        onDelete = { viewModel.onAction(CoursesAction.DeleteCourse(course.course)) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    AddCourseDialog(
        showDialog = showAddCourseDialog,
        onDismissRequest = { showAddCourseDialog = false },
        onAddCourse = { course, categories ->
            viewModel.onAction(CoursesAction.AddCourse(course, categories))
            showAddCourseDialog = false
        }
    )
}