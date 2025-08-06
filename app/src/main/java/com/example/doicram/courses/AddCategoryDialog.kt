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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.doicram.db.entities.CategoryColor
import com.example.doicram.db.entities.GradeCategories


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