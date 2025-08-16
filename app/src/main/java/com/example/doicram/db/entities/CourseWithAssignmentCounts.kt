package com.example.doicram.db.entities

import androidx.room.Embedded

data class CourseWithAssignmentCounts(
    @Embedded val course: Courses,
    val totalAssignments: Int,
    val completedAssignments: Int
)