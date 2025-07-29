package com.example.doicram.courses

import com.example.doicram.courses.db.entities.CourseWithCategories

data class CoursesState(
    val courses: List<CourseWithCategories> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)