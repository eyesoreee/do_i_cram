package com.example.doicram.grade_calculator

data class GpaAssignment(
    val name: String,
    val category: String,
    val score: Int,
    val maxScore: Int,
    val notes: String? = null,
)