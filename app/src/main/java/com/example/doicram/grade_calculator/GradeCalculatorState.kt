package com.example.doicram.grade_calculator

data class GradeCalculatorState(
    val cgpa: Double? = null,
    val totalCourses: Int = 0,
    val totalUnits: Int = 0,
    val courses: List<CalculatorCourse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)