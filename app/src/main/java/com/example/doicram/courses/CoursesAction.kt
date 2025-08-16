package com.example.doicram.courses

import com.example.doicram.db.entities.Assignments
import com.example.doicram.db.entities.Courses
import com.example.doicram.db.entities.GradeCategories
import com.example.doicram.db.entities.GradeScale

enum class SortOption {
    NAME_ASC,
    NAME_DESC,
    DATE_ADDED_ASC,
    DATE_ADDED_DESC,
    ASSIGNMENTS_COUNT_ASC,
    ASSIGNMENTS_COUNT_DESC,
    UNITS_ASC,
    UNITS_DESC,
    GRADE_ASC,
    GRADE_DESC
}

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
    data class UpdateSearchQuery(val query: String) : CoursesAction
    data class UpdateSortOption(val sortOption: SortOption) : CoursesAction
    data class ToggleShowArchived(val showArchived: Boolean) : CoursesAction
    data class ArchiveCourse(val course: Courses) : CoursesAction
    data class UnarchiveCourse(val course: Courses) : CoursesAction
}