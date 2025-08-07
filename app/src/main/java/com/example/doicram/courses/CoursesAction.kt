package com.example.doicram.courses

import com.example.doicram.db.entities.Assignments
import com.example.doicram.db.entities.Courses
import com.example.doicram.db.entities.GradeCategories
import com.example.doicram.db.entities.GradeScale

sealed interface CoursesAction {
    data class AddCourse(
        val course: Courses,
        val categories: List<GradeCategories>,
        val scales: List<GradeScale>
    ) : CoursesAction

    data class UpdateCourse(
        val course: Courses,
        val categories: List<GradeCategories>,
        val scales: List<GradeScale>
    ) : CoursesAction

    data class DeleteCourse(val course: Courses) : CoursesAction
    data class SelectCourse(val courseId: Int) : CoursesAction
    data object DeselectCourse : CoursesAction
    data class AddAssignment(val assignment: Assignments) : CoursesAction
    data class DeleteAssignment(val assignment: Assignments) : CoursesAction
    data class UpdateAssignment(val updatedAssignment: Assignments) : CoursesAction
}