package com.example.doicram.courses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.doicram.db.entities.Assignments
import com.example.doicram.db.entities.CategoryWithAssignments
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAssignmentDialog(
    showDialog: Boolean,
    assignment: Assignments?,
    categories: List<CategoryWithAssignments>,
    onDismissRequest: () -> Unit,
    onEditAssignment: (Assignments) -> Unit
) {
    if (showDialog && assignment != null) {
        var name by remember { mutableStateOf("") }
        var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
        var maxScore by remember { mutableStateOf("") }
        var score by remember { mutableStateOf("") }
        var dueDate by remember { mutableStateOf<Long?>(null) }
        var notes by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }
        var showDatePicker by remember { mutableStateOf(false) }

        LaunchedEffect(assignment) {
            name = assignment.name
            selectedCategoryId = assignment.categoryId
            maxScore = assignment.maxScore.toString()
            score = assignment.score?.toString() ?: ""
            dueDate = assignment.dueDate
            notes = assignment.notes ?: ""
        }

        val isFormValid = name.isNotBlank() &&
                selectedCategoryId != null &&
                maxScore.isNotBlank() &&
                maxScore.toIntOrNull() != null

        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(
                    text = "Edit Assignment",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = {
                            Text(
                                "Assignment Name *",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        },
                        placeholder = { Text("e.g., Midterm Exam 1") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = categories.find { it.category.id == selectedCategoryId }?.category?.name
                                ?: "",
                            onValueChange = { },
                            readOnly = true,
                            label = {
                                Text(
                                    "Category *",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            },
                            placeholder = { Text("Select a category") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor(MenuAnchorType.PrimaryEditable)
                                .fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            categories.forEach { categoryWithAssignments ->
                                DropdownMenuItem(
                                    text = { Text(categoryWithAssignments.category.name) },
                                    onClick = {
                                        selectedCategoryId = categoryWithAssignments.category.id
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = maxScore,
                        onValueChange = { input ->
                            maxScore = input.filter { it.isDigit() }
                        },
                        label = {
                            Text(
                                "Max Score *",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        },
                        placeholder = { Text("e.g., 100") },
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
                        value = score,
                        onValueChange = { input ->
                            score = input.filter { it.isDigit() }
                        },
                        label = {
                            Text(
                                "Current Score",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        },
                        placeholder = { Text("Leave empty if not graded yet") },
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
                        value = dueDate?.let {
                            SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(it))
                        } ?: "",
                        onValueChange = { },
                        readOnly = true,
                        label = {
                            Text(
                                "Due Date",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        },
                        placeholder = { Text("Optional") },
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Select date"
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showDatePicker = true },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )

                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = {
                            Text(
                                "Notes",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        },
                        placeholder = { Text("Additional notes (optional)") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        selectedCategoryId?.let { categoryId ->
                            val updatedAssignment = assignment.copy(
                                categoryId = categoryId,
                                name = name.trim(),
                                maxScore = maxScore.toInt(),
                                score = score.takeIf { it.isNotBlank() }?.toIntOrNull(),
                                dueDate = dueDate,
                                notes = notes.takeIf { it.isNotBlank() }
                            )
                            onEditAssignment(updatedAssignment)
                            onDismissRequest()
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
                        "Update Assignment",
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

        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = dueDate
            )
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            dueDate = datePickerState.selectedDateMillis
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}