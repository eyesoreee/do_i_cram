package com.example.doicram.courses

import com.example.doicram.courses.db.entities.Courses

data class CoursesState(
    val courses: List<Courses> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)