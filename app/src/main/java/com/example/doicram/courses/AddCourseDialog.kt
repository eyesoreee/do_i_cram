package com.example.doicram.courses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.doicram.courses.db.entities.CategoryColor
import com.example.doicram.courses.db.entities.Courses
import com.example.doicram.courses.db.entities.GradeCategories

@Composable
fun AddCourseDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onAddCourse: (Courses, List<GradeCategories>) -> Unit
) {
    if (showDialog) {
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        var courseName by remember { mutableStateOf("") }
        var courseCode by remember { mutableStateOf("") }
        var courseUnits by remember { mutableStateOf("") }
        var coursePassingGrade by remember { mutableStateOf("") }
        var categories by remember { mutableStateOf(listOf<GradeCategories>()) }

        val isFormValid =
            courseName.isNotBlank() && courseCode.isNotBlank() && courseUnits.isNotBlank()

        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(
                    text = "Add New Course",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Column {
                    TabRow(selectedTabIndex = selectedTabIndex) {
                        Tab(
                            selected = selectedTabIndex == 0,
                            onClick = { selectedTabIndex = 0 },
                            text = { Text("Basic Info") }
                        )
                        Tab(
                            selected = selectedTabIndex == 1,
                            onClick = { selectedTabIndex = 1 },
                            text = { Text("Category") }
                        )
                    }
                    when (selectedTabIndex) {
                        0 -> {
                            // Basic Info Tab
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                OutlinedTextField(
                                    value = courseName,
                                    onValueChange = { courseName = it },
                                    label = { Text("Course Name *") },
                                    placeholder = { Text("e.g., Computer Programming 1") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true
                                )
                                OutlinedTextField(
                                    value = courseCode,
                                    onValueChange = { courseCode = it.uppercase() },
                                    label = { Text("Course Code *") },
                                    placeholder = { Text("e.g., CCC101") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true
                                )
                                OutlinedTextField(
                                    value = courseUnits,
                                    onValueChange = { it ->
                                        courseUnits = it.filter { it.isDigit() }
                                    },
                                    label = { Text("Course Units *") },
                                    placeholder = { Text("e.g., 3") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true
                                )
                                OutlinedTextField(
                                    value = coursePassingGrade,
                                    onValueChange = { it ->
                                        coursePassingGrade = it.filter { it.isDigit() }
                                    },
                                    label = { Text("Passing Grade (%)") },
                                    placeholder = { Text("e.g., 60") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    supportingText = { Text("Optional (0-100%)") }
                                )
                            }
                        }

                        1 -> {
                            // Category Tab
                            Column {
                                Text(
                                    "Grade Categories",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                val totalWeight = categories.sumOf { it.weight }
                                Text(
                                    "Total weight: $totalWeight%",
                                    color = if (totalWeight == 100.0) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                categories.forEach { category ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier
                                                    .size(16.dp)
                                                    .background(category.color, shape = CircleShape)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(category.name)
                                        }
                                        Text("${category.weight}%")
                                        IconButton(onClick = {
                                            categories = categories.filter { it != category }
                                        }) {
                                            Icon(
                                                Icons.Default.Delete,
                                                contentDescription = "Delete"
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                var showAddCategoryDialog by remember { mutableStateOf(false) }
                                Button(onClick = { showAddCategoryDialog = true }) {
                                    Text("+ Add Category")
                                }

                                if (showAddCategoryDialog) {
                                    AddCategoryDialog(
                                        onDismiss = { showAddCategoryDialog = false },
                                        onAddCategory = { newCategory ->
                                            categories = categories + newCategory
                                            showAddCategoryDialog = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (isFormValid) {
                            val course = Courses(
                                name = courseName,
                                code = courseCode,
                                units = courseUnits.toInt(),
                                passingGrade = coursePassingGrade.toDoubleOrNull()
                            )
                            onAddCourse(course, categories)
                        }
                    },
                    enabled = isFormValid
                ) {
                    Text("Add Course")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun AddCategoryDialog(
    onDismiss: () -> Unit,
    onAddCategory: (GradeCategories) -> Unit
) {
    var categoryName by remember { mutableStateOf("") }
    var categoryWeight by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(CategoryColor.BLUE) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Category") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    label = { Text("Category Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = categoryWeight,
                    onValueChange = { it ->
                        categoryWeight = it.filter { it.isDigit() || it == '.' }
                    },
                    label = { Text("Weight (%)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                var expanded by remember { mutableStateOf(false) }
                Box {
                    TextButton(onClick = { expanded = true }) {
                        Text(selectedColor.name)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        CategoryColor.entries.forEach { color ->
                            DropdownMenuItem(
                                text = { Text(color.name) },
                                onClick = {
                                    selectedColor = color
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (categoryName.isNotBlank() && categoryWeight.isNotBlank()) {
                    val weight = categoryWeight.toDoubleOrNull()
                    if (weight != null) {
                        val newCategory = GradeCategories(
                            name = categoryName,
                            weight = weight,
                            colorName = selectedColor,
                            courseId = 0,
                        )
                        onAddCategory(newCategory)
                    }
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}