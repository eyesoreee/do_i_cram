package com.example.doicram.grade_calculator

data class GpaScale(
    val gpaValue: Double,
    val minPercentage: Double,
    val maxPercentage: Double,
    val letterGrade: String? = null,
    val description: String? = null,
    val isPassing: Boolean
)