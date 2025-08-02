package com.example.doicram.courses

import com.example.doicram.courses.db.entities.Assignments
import com.example.doicram.courses.db.entities.Courses
import com.example.doicram.courses.db.entities.GradeCategories

sealed interface CoursesAction {
    data class AddCourse(val course: Courses, val categories: List<GradeCategories>) : CoursesAction
    data class DeleteCourse(val course: Courses) : CoursesAction
    data class SelectCourse(val courseId: Int) : CoursesAction
    data object DeselectCourse : CoursesAction

    data class AddAssignment(val assignment: Assignments) : CoursesAction
    data class DeleteAssignment(val assignment: Assignments) : CoursesAction
    data class UpdateAssignment(val updatedAssignment: Assignments) : CoursesAction
}