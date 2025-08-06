package com.example.doicram.courses

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.School
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.doicram.Loading
import com.example.doicram.PageHeader
import com.example.doicram.db.entities.Assignments
import com.example.doicram.db.entities.Courses

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

    var selectedDetailedTabIndex by remember { mutableIntStateOf(0) }
    var showAddAssignmentDialog by remember { mutableStateOf(false) }

    var showEditAssignmentDialog by remember { mutableStateOf(false) }
    var assignmentToEdit by remember { mutableStateOf<Assignments?>(null) }

    var showDeleteCourseDialog by remember { mutableStateOf(false) }
    var courseToDelete by remember { mutableStateOf<Courses?>(null) }

    var showDeleteAssignmentDialog by remember { mutableStateOf(false) }
    var assignmentToDelete by remember { mutableStateOf<Assignments?>(null) }

    LazyColumn(modifier = modifier.fillMaxSize()) {
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

        if (selectedTabIndex == -1) {
            item {
                PageHeader(
                    title = "Course Management",
                    subtitle = "Add, edit, and configure your courses and grading categories."
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

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
                    CourseHeaderCard(state.selectedCourse!!)
                }

                item {
                    CourseTab(
                        selectedTabIndex = selectedDetailedTabIndex,
                        tabs = listOf("Overview", "Assignments", "Settings"),
                        onTabSelected = { index ->
                            selectedDetailedTabIndex = index
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }


                when (selectedDetailedTabIndex) {
                    // Overview Content
                    0 -> {
                        item {
                            OverviewContent(state.selectedCourse!!)
                        }
                    }

                    // Assignments Content
                    1 -> {
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
                                        text = "Add Assignment",
                                        color = MaterialTheme.colorScheme.inverseSurface
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        items(state.selectedCourse!!.categoriesWithAssignments) { categoryWithAssignments ->
                            CategoryAssignmentCard(
                                categoryWithAssignments = categoryWithAssignments,
                                onDeleteClick = { assignment ->
                                    assignmentToDelete = assignment
                                    showDeleteAssignmentDialog = true
                                },
                                onEditClick = { assignment ->
                                    assignmentToEdit = assignment
                                    showEditAssignmentDialog = true
                                }
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    // Course Settings Content
                    else -> {
                        item {
                            Text(
                                text = "Course Settings",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        item {
                            // Basic Information Card
                            SettingsCard(
                                title = "Basic Information",
                                icon = Icons.Default.School
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    InfoRow(
                                        label = "Course Name",
                                        value = state.selectedCourse!!.course.name
                                    )
                                    InfoRow(
                                        label = "Course Code",
                                        value = state.selectedCourse!!.course.code
                                    )
                                    InfoRow(
                                        label = "Course Units",
                                        value = "${state.selectedCourse!!.course.units}"
                                    )
                                    InfoRow(
                                        label = "Target Grade",
                                        value = "${state.selectedCourse!!.course.targetGrade ?: "Not Set"}",
                                        valueColor = if (state.selectedCourse!!.course.targetGrade != null)
                                            MaterialTheme.colorScheme.primary else
                                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            }
                        }

                        item {
                            // Grading Scale Card
                            SettingsCard(
                                title = "Grading Scale",
                                icon = Icons.Default.Grade
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    state.selectedCourse!!.gradeScales.forEach { scale ->
                                        GradeScaleRow(scale)
                                    }
                                }
                            }
                        }

                        item {
                            // Grade Categories Card
                            SettingsCard(
                                title = "Grade Categories",
                                icon = Icons.Default.Category
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    state.selectedCourse!!.categoriesWithAssignments.forEach { (category, _) ->
                                        SettingsCategoryRow(category)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            else -> {
                items(state.courses) { course ->
                    CourseCard(
                        course = course,
                        onEdit = { },
                        onDelete = { course ->
                            courseToDelete = course
                            showDeleteCourseDialog = true
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    AddCourseDialog(
        showDialog = showAddCourseDialog,
        onDismissRequest = { showAddCourseDialog = false },
        onAddCourse = { course, categories, scales ->
            viewModel.onAction(CoursesAction.AddCourse(course, categories, scales))
            showAddCourseDialog = false
        }
    )

    AddAssignmentDialog(
        showDialog = showAddAssignmentDialog,
        categories = state.selectedCourse?.categoriesWithAssignments ?: emptyList(),
        onDismissRequest = { showAddAssignmentDialog = false },
        onAddAssignment = { assignments ->
            viewModel.onAction(CoursesAction.AddAssignment(assignments))
            showAddAssignmentDialog = false
        },
    )

    EditAssignmentDialog(
        showDialog = showEditAssignmentDialog,
        assignment = assignmentToEdit,
        categories = state.selectedCourse?.categoriesWithAssignments ?: emptyList(),
        onDismissRequest = {
            showEditAssignmentDialog = false
            assignmentToEdit = null
        },
        onEditAssignment = { updatedAssignment ->
            viewModel.onAction(CoursesAction.UpdateAssignment(updatedAssignment))
            showEditAssignmentDialog = false
            assignmentToEdit = null
        }
    )

    DeleteCourseDialog(
        showDialog = showDeleteCourseDialog,
        onDismissRequest = { showDeleteCourseDialog = showDeleteCourseDialog.not() },
        onDelete = {
            courseToDelete.let {
                viewModel.onAction(CoursesAction.DeleteCourse(courseToDelete!!))
            }
            showDeleteCourseDialog = false
            courseToDelete = null
        }
    )

    DeleteAssignmentDialog(
        showDialog = showDeleteAssignmentDialog,
        onDismissRequest = { showDeleteAssignmentDialog = showDeleteAssignmentDialog.not() },
        onDelete = {
            assignmentToDelete.let {
                viewModel.onAction(CoursesAction.DeleteAssignment(assignmentToDelete!!))
            }
            showDeleteAssignmentDialog = false
            assignmentToDelete = null
        }
    )
}