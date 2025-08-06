package com.example.doicram.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "grade_scales",
    foreignKeys = [
        ForeignKey(
            entity = Courses::class,
            parentColumns = ["id"],
            childColumns = ["course_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["min_percentage", "max_percentage"], unique = false),
        Index(value = ["is_default"]),
        Index(value = ["course_id"])
    ]
)
data class GradeScale(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "course_id")
    val courseId: Int? = null,

    @ColumnInfo(name = "gpa_value")
    val gpaValue: Double,

    @ColumnInfo(name = "min_percentage")
    val minPercentage: Double,

    @ColumnInfo(name = "max_percentage")
    val maxPercentage: Double,

    @ColumnInfo(name = "letter_grade")
    val letterGrade: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "is_passing")
    val isPassing: Boolean,

    @ColumnInfo(name = "is_default")
    val isDefault: Boolean = false,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
) {
    companion object {
        fun getGpaForPercentage(percentage: Double, scales: List<GradeScale>): GradeScale? {
            return scales.find { scale ->
                percentage >= scale.minPercentage && percentage <= scale.maxPercentage
            }
        }

        fun calculateGpa(percentage: Double, scales: List<GradeScale>): Double? {
            return getGpaForPercentage(percentage, scales)?.gpaValue
        }

        fun getDefaultScale(): List<GradeScale> {
            return listOf(
                GradeScale(
                    gpaValue = 1.00,
                    minPercentage = 97.0,
                    maxPercentage = 100.0,
                    description = "Excellent",
                    isPassing = true,
                    isDefault = true
                ),
                GradeScale(
                    gpaValue = 1.25,
                    minPercentage = 94.0,
                    maxPercentage = 96.9,
                    description = "Excellent",
                    isPassing = true,
                    isDefault = true
                ),
                GradeScale(
                    gpaValue = 1.50,
                    minPercentage = 91.0,
                    maxPercentage = 93.9,
                    description = "Excellent",
                    isPassing = true,
                    isDefault = true
                ),
                GradeScale(
                    gpaValue = 1.75,
                    minPercentage = 88.0,
                    maxPercentage = 90.9,
                    description = "Very Good",
                    isPassing = true,
                    isDefault = true
                ),
                GradeScale(
                    gpaValue = 2.00,
                    minPercentage = 85.0,
                    maxPercentage = 87.9,
                    description = "Very Good",
                    isPassing = true,
                    isDefault = true
                ),
                GradeScale(
                    gpaValue = 2.25,
                    minPercentage = 82.0,
                    maxPercentage = 84.9,
                    description = "Good",
                    isPassing = true,
                    isDefault = true
                ),
                GradeScale(
                    gpaValue = 2.50,
                    minPercentage = 79.0,
                    maxPercentage = 81.9,
                    description = "Satisfactory",
                    isPassing = true,
                    isDefault = true
                ),
                GradeScale(
                    gpaValue = 2.75,
                    minPercentage = 76.0,
                    maxPercentage = 78.9,
                    description = "Satisfactory",
                    isPassing = true,
                    isDefault = true
                ),
                GradeScale(
                    gpaValue = 3.00,
                    minPercentage = 75.0,
                    maxPercentage = 75.9,
                    description = "Passing",
                    isPassing = true,
                    isDefault = true
                ),
                GradeScale(
                    gpaValue = 5.00,
                    minPercentage = 0.0,
                    maxPercentage = 74.9,
                    description = "Failure",
                    isPassing = false,
                    isDefault = true
                )
            )
        }
    }
}