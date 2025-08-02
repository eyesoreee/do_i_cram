package com.example.doicram.courses.db.entities

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

enum class CategoryColor {
    RED, GREEN, BLUE, ORANGE, PURPLE, TEAL, PINK, INDIGO
}

@Entity(
    tableName = "grade_categories",
    foreignKeys = [
        ForeignKey(
            entity = Courses::class,
            parentColumns = ["id"],
            childColumns = ["course_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["course_id"])]
)
data class GradeCategories(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "course_id")
    val courseId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "weight")
    val weight: Double,

    @ColumnInfo(name = "current_grade")
    val currentGrade: Double? = null,

    @ColumnInfo(name = "target_grade")
    val targetGrade: Double? = null,

    @ColumnInfo(name = "color_name")
    val colorName: CategoryColor = CategoryColor.BLUE,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "archived_at")
    val archivedAt: Long? = null
) {
    val color: Color
        get() = when (colorName) {
            CategoryColor.RED -> Color.Red
            CategoryColor.GREEN -> Color.Green
            CategoryColor.BLUE -> Color.Blue
            CategoryColor.ORANGE -> Color(0xFFFF9800)
            CategoryColor.PURPLE -> Color(0xFF9C27B0)
            CategoryColor.TEAL -> Color(0xFF009688)
            CategoryColor.PINK -> Color(0xFFE91E63)
            CategoryColor.INDIGO -> Color(0xFF3F51B5)
        }
}