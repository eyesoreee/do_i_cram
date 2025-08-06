package com.example.doicram.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithAssignments(
    @Embedded val category: GradeCategories,
    @Relation(
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val assignments: List<Assignments>
) {
    fun calculateCategoryAverage(): Double? {
        val gradedAssignments =
            assignments.filter { it.score != null && it.percentageScore != null }
        return if (gradedAssignments.isNotEmpty()) {
            gradedAssignments.mapNotNull { it.percentageScore }.average()
        } else null
    }

    val totalPossiblePoints: Int
        get() = assignments.sumOf { it.maxScore }

    val totalEarnedPoints: Int?
        get() = assignments.mapNotNull { it.score }.takeIf { it.isNotEmpty() }?.sum()
}