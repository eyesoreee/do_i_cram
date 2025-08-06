package com.example.doicram.db.entities

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
            CategoryColor.RED -> Color(0xFFD32F2F)
            CategoryColor.GREEN -> Color(0xFF388E3C)
            CategoryColor.BLUE -> Color(0xFF1976D2)
            CategoryColor.ORANGE -> Color(0xFFF57C00)
            CategoryColor.PURPLE -> Color(0xFF7B1FA2)
            CategoryColor.TEAL -> Color(0xFF00796B)
            CategoryColor.PINK -> Color(0xFFC2185B)
            CategoryColor.INDIGO -> Color(0xFF303F9F)
        }

    fun getGpaEquivalent(gradeScale: List<GradeScale>): Double? {
        return currentGrade?.let { grade ->
            GradeScale.calculateGpa(grade, gradeScale)
        }
    }

    fun isPassing(gradeScale: List<GradeScale>): Boolean? {
        return currentGrade?.let { grade ->
            GradeScale.getGpaForPercentage(grade, gradeScale)?.isPassing
        }
    }

    companion object {
        fun getDefaultCategories(): List<GradeCategories> {
            return listOf(
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
        }
    }
}