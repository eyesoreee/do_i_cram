package com.example.doicram.grade_calculator

import androidx.compose.ui.graphics.Color
import com.example.doicram.db.entities.CategoryColor

data class GpaCategory(
    val name: String,
    val weight: Double,
    val grade: Double? = null,
    val assignments: List<GpaAssignment> = emptyList(),
    val colorName: CategoryColor = CategoryColor.BLUE
) {
    val color: Color
        get() = when (colorName) {
            CategoryColor.RED -> Color(0xFFD32F2F)
            CategoryColor.GREEN -> Color(0xFF388E3C)
            CategoryColor.BLUE -> Color(0xFF1976D2)
            CategoryColor.ORANGE -> Color(0xFFF57C00)
            CategoryColor.PURPLE -> Color(0xFF7B1FA2)
            CategoryColor.TEAL -> Color(0xFF00796B)
            CategoryColor.PINK -> Color(0xFFC2185B)
            CategoryColor.INDIGO -> Color(0xFF303F9F)
        }
}