package com.example.doicram.grade_calculator

sealed interface GradeCalculatorAction {
    data class OnAddCourseClick(val course: CalculatorCourse) : GradeCalculatorAction
    data class OnDeleteCourseClick(val course: CalculatorCourse) : GradeCalculatorAction
    data class OnAddAssignmentClick(val assignment: GpaAssignment) : GradeCalculatorAction
    data class OnDeleteAssignmentClick(val assignment: GpaAssignment) : GradeCalculatorAction
    data class OnEditClick(val categories: List<GpaCategory>, val scales: List<GpaScale>) :
        GradeCalculatorAction
}