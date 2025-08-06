package com.example.doicram.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CourseWithCategories(
    @Embedded val course: Courses,
    @Relation(
        parentColumn = "id",
        entityColumn = "course_id",
        entity = GradeCategories::class
    )
    val categories: List<GradeCategories>,
    @Relation(
        parentColumn = "id",
        entityColumn = "category_id",
        entity = Assignments::class
    )
    val assignments: List<Assignments>
) {
    val totalAssignments: Int get() = assignments.size
    val gradedAssignments: Int get() = assignments.count { it.score != null }
}