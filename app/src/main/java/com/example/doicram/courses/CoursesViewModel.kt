package com.example.doicram.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doicram.courses.db.entities.Courses
import com.example.doicram.courses.db.entities.GradeCategories
import com.example.doicram.courses.db.repo.CoursesRepository
import com.example.doicram.courses.db.repo.GradeCategoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val coursesRepository: CoursesRepository,
    private val categoriesRepository: GradeCategoriesRepository  // Add this
) : ViewModel() {
    private val _state: MutableStateFlow<CoursesState> = MutableStateFlow(CoursesState())
    val state: StateFlow<CoursesState> = _state.asStateFlow()

    init {
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val fetchedCourses = coursesRepository.getCourses()
                _state.update {
                    it.copy(
                        courses = fetchedCourses,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to load courses: ${e.localizedMessage ?: "Unknown error"}"
                    )
                }
                e.printStackTrace()
            }
        }
    }

    fun onAction(action: CoursesAction) {
        when (action) {
            is CoursesAction.AddCourse -> addCourseWithCategories(action.course, action.categories)
            is CoursesAction.DeleteCourse -> deleteCourse(action.course)
            is CoursesAction.SelectCourse -> selectCourse(action.courseId)
            is CoursesAction.DeselectCourse -> _state.update { it.copy(selectedCourse = null) }
        }
    }

    private fun addCourseWithCategories(course: Courses, categories: List<GradeCategories>) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val courseId = coursesRepository.addCourse(course)

                val categoriesWithId = categories.map { it.copy(courseId = courseId.toInt()) }
                categoriesWithId.forEach { categoriesRepository.addCategory(it) }

                loadCourses()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to add course or categories: ${e.localizedMessage ?: "Unknown error"}"
                    )
                }
                e.printStackTrace()
            }
        }
    }

    private fun deleteCourse(course: Courses) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                coursesRepository.deleteCourse(course)
                loadCourses()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to delete course: ${e.localizedMessage ?: "Unknown error"}"
                    )
                }
                e.printStackTrace()
            }
        }
    }

    private fun selectCourse(courseId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val selectedCourse = coursesRepository.getCourseWithFullDetails(courseId)
                _state.update {
                    it.copy(
                        selectedCourse = selectedCourse,
                        isLoading = false,
                        error = null
                    )
                }
                loadCourses()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to select course: ${e.localizedMessage ?: "Unknown error"}"
                    )
                }
                e.printStackTrace()
            }
        }
    }
}