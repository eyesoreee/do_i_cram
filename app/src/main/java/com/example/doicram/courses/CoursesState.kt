package com.example.doicram.courses

import com.example.doicram.db.entities.CourseWithCategories
import com.example.doicram.db.entities.CourseWithFullDetails

data class CoursesState(
    val courses: List<CourseWithCategories> = emptyList(),
    val selectedCourse: CourseWithFullDetails? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)