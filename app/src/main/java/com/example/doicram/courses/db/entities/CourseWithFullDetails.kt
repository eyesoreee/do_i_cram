package com.example.doicram.courses.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CourseWithFullDetails(
    @Embedded val course: Courses,
    @Relation(
        entity = GradeCategories::class,
        parentColumn = "id",
        entityColumn = "course_id"
    )
    val categoriesWithAssignments: List<CategoryWithAssignments>
)
