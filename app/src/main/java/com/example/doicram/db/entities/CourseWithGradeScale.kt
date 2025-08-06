package com.example.doicram.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CourseWithGradeScale(
    @Embedded val course: Courses,

    @Relation(
        parentColumn = "id",
        entityColumn = "course_id"
    )
    val gradeScales: List<GradeScale>
)