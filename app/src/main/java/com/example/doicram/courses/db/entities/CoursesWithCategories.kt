package com.example.doicram.courses.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CourseWithCategories(
    @Embedded val course: Courses,
    @Relation(
        parentColumn = "id",
        entityColumn = "course_id"
    )
    val categories: List<GradeCategories>
)