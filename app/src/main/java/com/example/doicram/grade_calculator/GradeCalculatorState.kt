package com.example.doicram.grade_calculator

data class GradeCalculatorState(
    // CGPA
    val cgpa: Double? = null,
    val totalCourses: Int = 0,
    val totalUnits: Int = 0,
    val courses: List<CalculatorCourse> = emptyList(),

    // GPA
    val categories: List<GpaCategory> = emptyList(),
    val scales: List<GpaScale> = emptyList(),
    val gpa: Double? = null,

    val isLoading: Boolean = false,
    val error: String? = null,
)