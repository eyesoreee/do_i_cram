package com.example.doicram.courses

import com.example.doicram.db.entities.CourseWithCategoryAndScale
import com.example.doicram.db.entities.CourseWithFullDetails

data class CoursesState(
    val courses: List<CourseWithCategoryAndScale> = emptyList(),
    val selectedCourse: CourseWithFullDetails? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val showArchived: Boolean = false,
    val sortOption: SortOption = SortOption.NAME_ASC,
) {
    val displayCourses: List<CourseWithCategoryAndScale>
        get() {
            val filtered = if (searchQuery.isBlank()) {
                courses
            } else {
                courses.filter { course ->
                    course.course.name.contains(searchQuery, ignoreCase = true) ||
                            course.course.code.contains(searchQuery, ignoreCase = true) ||
                            course.course.description?.contains(
                                searchQuery,
                                ignoreCase = true
                            ) == true
                }
            }

            return when (sortOption) {
                SortOption.NAME_ASC -> filtered.sortedBy { it.course.name.lowercase() }
                SortOption.NAME_DESC -> filtered.sortedByDescending { it.course.name.lowercase() }
                SortOption.DATE_ADDED_ASC -> filtered.sortedBy { it.course.createdAt }
                SortOption.DATE_ADDED_DESC -> filtered.sortedByDescending { it.course.createdAt }
                SortOption.ASSIGNMENTS_COUNT_ASC -> filtered.sortedBy { it.totalAssignments }
                SortOption.ASSIGNMENTS_COUNT_DESC -> filtered.sortedByDescending { it.totalAssignments }
                SortOption.UNITS_ASC -> filtered.sortedBy { it.course.units }
                SortOption.UNITS_DESC -> filtered.sortedByDescending { it.course.units }
                SortOption.GRADE_ASC -> filtered.sortedBy { it.course.grade ?: Double.MAX_VALUE }
                SortOption.GRADE_DESC -> filtered.sortedByDescending { it.course.grade ?: -1.0 }
            }
        }
}