package com.example.doicram.grade_calculator

sealed interface GradeCalculatorAction {
    data class OnAddCourseClick(val course: CalculatorCourse) : GradeCalculatorAction
    data class OnDeleteCourseClick(val course: CalculatorCourse) : GradeCalculatorAction
}