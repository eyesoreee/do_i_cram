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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
        var categories by remember {
            mutableStateOf(
                listOf(
                    GradeCategories(
                        courseId = 0,
                        name = "Preliminary Exam",
                        weight = 20.0,
                        colorName = CategoryColor.BLUE
                    ),
                    GradeCategories(
                        courseId = 0,
                        name = "Midterm Exam",
                        weight = 20.0,
                        colorName = CategoryColor.GREEN
                    ),
                    GradeCategories(
                        courseId = 0,
                        name = "Final Exam",
                        weight = 20.0,
                        colorName = CategoryColor.RED
                    ),
                    GradeCategories(
                        courseId = 0,
                        name = "Project",
                        weight = 25.0,
                        colorName = CategoryColor.PURPLE
                    ),
                    GradeCategories(
                        courseId = 0,
                        name = "Activities and Quizzes",
                        weight = 15.0,
                        colorName = CategoryColor.ORANGE

                    )
                )
            )
        }

        val isFormValid =
            courseName.isNotBlank() && courseCode.isNotBlank() && courseUnits.isNotBlank()

        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(
                    text = "Add New Course",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Column {
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
                        Tab(
                            selected = selectedTabIndex == 0,
                            onClick = { selectedTabIndex = 0 },
                            text = {
                                Text(
                                    "Basic Info",
                                    fontWeight = if (selectedTabIndex == 0) FontWeight.SemiBold else FontWeight.Normal
                                )
                            },
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                        Tab(
                            selected = selectedTabIndex == 1,
                            onClick = { selectedTabIndex = 1 },
                            text = {
                                Text(
                                    "Categories",
                                    fontWeight = if (selectedTabIndex == 1) FontWeight.SemiBold else FontWeight.Normal
                                )
                            },
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    when (selectedTabIndex) {
                        0 -> {
                            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                                OutlinedTextField(
                                    value = courseName,
                                    onValueChange = { courseName = it },
                                    label = {
                                        Text(
                                            "Course Name *",
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    },
                                    placeholder = { Text("e.g., Introduction to Computer Science") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        focusedLabelColor = MaterialTheme.colorScheme.primary
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                OutlinedTextField(
                                    value = courseCode,
                                    onValueChange = { courseCode = it.uppercase() },
                                    label = {
                                        Text(
                                            "Course Code *",
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    },
                                    placeholder = { Text("e.g., CS 101") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        focusedLabelColor = MaterialTheme.colorScheme.primary
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                OutlinedTextField(
                                    value = courseUnits,
                                    onValueChange = { it ->
                                        courseUnits = it.filter { it.isDigit() }
                                    },
                                    label = {
                                        Text(
                                            "Credits *",
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    },
                                    placeholder = { Text("3") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        focusedLabelColor = MaterialTheme.colorScheme.primary
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                OutlinedTextField(
                                    value = coursePassingGrade,
                                    onValueChange = { it ->
                                        coursePassingGrade = it.filter { it.isDigit() }
                                    },
                                    label = {
                                        Text(
                                            "Passing Grade (%)",
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    },
                                    placeholder = { Text("90") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    supportingText = {
                                        Text(
                                            "Optional (0-100%)",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        focusedLabelColor = MaterialTheme.colorScheme.primary
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                            }
                        }

                        1 -> {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "Grade Categories",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                    val totalWeight = categories.sumOf { it.weight }
                                    Text(
                                        "Total weight: ${totalWeight.toInt()}%",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Medium
                                        ),
                                        color = if (totalWeight == 100.0)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.error
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                categories.forEach { category ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                                alpha = 0.3f
                                            )
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(12.dp)
                                                        .background(
                                                            category.color,
                                                            shape = CircleShape
                                                        )
                                                )
                                                Spacer(modifier = Modifier.width(12.dp))
                                                Text(
                                                    category.name,
                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                        fontWeight = FontWeight.Medium
                                                    )
                                                )
                                            }
                                            Text(
                                                "${category.weight.toInt()}%",
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    fontWeight = FontWeight.SemiBold
                                                ),
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            IconButton(
                                                onClick = {
                                                    categories =
                                                        categories.filter { it != category }
                                                }
                                            ) {
                                                Icon(
                                                    Icons.Default.Delete,
                                                    contentDescription = "Delete",
                                                    tint = MaterialTheme.colorScheme.error
                                                )
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                var showAddCategoryDialog by remember { mutableStateOf(false) }
                                Button(
                                    onClick = { showAddCategoryDialog = true },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        "Add Category",
                                        fontWeight = FontWeight.Medium
                                    )
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
                Button(
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
                    enabled = isFormValid,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Add Course",
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Text("Cancel")
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(16.dp)
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
        title = {
            Text(
                "Add Category",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Medium
                )
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    label = {
                        Text(
                            "Category Name",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    },
                    placeholder = { Text("e.g., Exams") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                OutlinedTextField(
                    value = categoryWeight,
                    onValueChange = { it ->
                        categoryWeight = it.filter { it.isDigit() || it == '.' }
                    },
                    label = {
                        Text(
                            "Weight (%)",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    },
                    placeholder = { Text("40") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                // Enhanced color picker
                Column {
                    Text(
                        "Color",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    var expanded by remember { mutableStateOf(false) }
                    Box {
                        Card(
                            onClick = { expanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .background(
                                            when (selectedColor) {
                                                CategoryColor.RED -> Color.Red
                                                CategoryColor.GREEN -> Color.Green
                                                CategoryColor.BLUE -> Color.Blue
                                                CategoryColor.ORANGE -> Color(0xFFFF9800)
                                                CategoryColor.PURPLE -> Color(0xFF9C27B0)
                                                CategoryColor.TEAL -> Color(0xFF009688)
                                                CategoryColor.PINK -> Color(0xFFE91E63)
                                                CategoryColor.INDIGO -> Color(0xFF3F51B5)
                                            },
                                            shape = CircleShape
                                        )
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    selectedColor.name,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            CategoryColor.entries.forEach { color ->
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier
                                                    .size(16.dp)
                                                    .background(
                                                        when (color) {
                                                            CategoryColor.RED -> Color.Red
                                                            CategoryColor.GREEN -> Color.Green
                                                            CategoryColor.BLUE -> Color.Blue
                                                            CategoryColor.ORANGE -> Color(0xFFFF9800)
                                                            CategoryColor.PURPLE -> Color(0xFF9C27B0)
                                                            CategoryColor.TEAL -> Color(0xFF009688)
                                                            CategoryColor.PINK -> Color(0xFFE91E63)
                                                            CategoryColor.INDIGO -> Color(0xFF3F51B5)
                                                        },
                                                        shape = CircleShape
                                                    )
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(color.name)
                                        }
                                    },
                                    onClick = {
                                        selectedColor = color
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
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
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Add",
                    fontWeight = FontWeight.Medium
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text("Cancel")
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp)
    )
}