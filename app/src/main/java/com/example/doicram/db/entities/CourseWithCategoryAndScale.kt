package com.example.doicram.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CourseWithCategoryAndScale(
    @Embedded
    val course: Courses,

    @Relation(
        parentColumn = "id",
        entityColumn = "course_id"
    )
    val categories: List<GradeCategories>,

    @Relation(
        parentColumn = "id",
        entityColumn = "course_id"
    )
    val scales: List<GradeScale>,
    val totalAssignments: Int = 0,
    val completedAssignments: Int = 0
)