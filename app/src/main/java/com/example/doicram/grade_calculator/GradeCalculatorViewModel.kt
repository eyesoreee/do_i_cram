package com.example.doicram.grade_calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GradeCalculatorViewModel : ViewModel() {
    private val _state = MutableStateFlow(GradeCalculatorState())
    val state: StateFlow<GradeCalculatorState> = _state.asStateFlow()

    fun onAction(action: GradeCalculatorAction) {
        when (action) {
            is GradeCalculatorAction.OnAddCourseClick -> {
                addCourse(action.course)
            }

            is GradeCalculatorAction.OnDeleteCourseClick -> {
                deleteCourse(action.course)
            }
        }
    }

    private fun addCourse(course: CalculatorCourse) {
        _state.update { currentState ->
            val newCourses = currentState.courses + course
            val newTotalUnits = currentState.totalUnits + course.units
            val newTotalGpa =
                (currentState.cgpa ?: 0.0) * currentState.totalUnits + (course.units * course.gpa)

            currentState.copy(
                courses = newCourses,
                totalCourses = newCourses.size,
                totalUnits = newTotalUnits,
                cgpa = if (newTotalUnits > 0) (newTotalGpa / newTotalUnits) else null
            )
        }
    }

    private fun deleteCourse(course: CalculatorCourse) {
        _state.update { currentState ->
            val newCourses = currentState.courses - course
            if (newCourses.isEmpty()) {
                currentState.copy(
                    courses = emptyList(),
                    totalCourses = 0,
                    totalUnits = 0,
                    cgpa = null
                )
            } else {
                val newTotalUnits = newCourses.sumOf { it.units }
                val newTotalGpa = newCourses.sumOf { it.units * it.gpa }

                currentState.copy(
                    courses = newCourses,
                    totalCourses = newCourses.size,
                    totalUnits = newTotalUnits,
                    cgpa = if (newTotalUnits > 0) newTotalGpa / newTotalUnits else null
                )
            }
        }
    }
}