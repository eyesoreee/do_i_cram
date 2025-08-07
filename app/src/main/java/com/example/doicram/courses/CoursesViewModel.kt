package com.example.doicram.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doicram.db.entities.Assignments
import com.example.doicram.db.entities.Courses
import com.example.doicram.db.entities.GradeCategories
import com.example.doicram.db.entities.GradeScale
import com.example.doicram.db.repo.AssignmentsRepository
import com.example.doicram.db.repo.CoursesRepository
import com.example.doicram.db.repo.GradeCategoriesRepository
import com.example.doicram.db.repo.GradeScaleRepository
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
    private val categoriesRepository: GradeCategoriesRepository,
    private val assignmentsRepository: AssignmentsRepository,
    private val scalesRepository: GradeScaleRepository,
    private val gradeCalculationService: GradeCalculationService
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
            is CoursesAction.AddCourse -> addCourse(action.course, action.categories, action.scales)
            is CoursesAction.DeleteCourse -> deleteCourse(action.course)
            is CoursesAction.UpdateCourse -> updateCourse(
                action.course,
                action.categories,
                action.scales
            )

            is CoursesAction.SelectCourse -> selectCourse(action.courseId)
            is CoursesAction.DeselectCourse -> _state.update { it.copy(selectedCourse = null) }

            is CoursesAction.AddAssignment -> addAssignment(action.assignment)
            is CoursesAction.DeleteAssignment -> deleteAssignment(action.assignment)
            is CoursesAction.UpdateAssignment -> updateAssignment(action.updatedAssignment)
        }
    }

    private fun addCourse(
        course: Courses,
        categories: List<GradeCategories>,
        scales: List<GradeScale>
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val courseId = coursesRepository.addCourse(course)

                val categoriesWithId = categories.map { it.copy(courseId = courseId.toInt()) }
                categoriesRepository.addCategories(categoriesWithId)

                val scalesWithId = scales.map { it.copy(courseId = courseId.toInt()) }
                scalesRepository.insertGradeScales(scalesWithId)

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

    private fun addAssignment(assignment: Assignments) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                assignmentsRepository.addAssignment(assignment)

                if (assignment.score != null) {
                    gradeCalculationService.recalculateGradesForCategory(assignment.categoryId)
                }

                refreshAssignment()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to add assignment: ${e.localizedMessage ?: "Unknown error"}"
                    )
                }
                e.printStackTrace()
            }
        }
    }

    private fun deleteAssignment(assignment: Assignments) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                assignmentsRepository.deleteAssignment(assignment)
                gradeCalculationService.recalculateGradesForCategory(assignment.categoryId)
                refreshAssignment()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to delete assignment: ${e.localizedMessage ?: "Unknown error"}"
                    )
                }
                e.printStackTrace()
            }
        }
    }

    private fun updateAssignment(assignment: Assignments) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val oldAssignment = assignmentsRepository.getAssignmentById(assignment.id)
                assignmentsRepository.updateAssignment(assignment)

                val scoreChanged = oldAssignment?.score != assignment.score
                if (scoreChanged) {
                    gradeCalculationService.recalculateGradesForCategory(assignment.categoryId)
                }

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = null
                    )
                }

                refreshAssignment()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to delete assignment: ${e.localizedMessage ?: "Unknown error"}"
                    )
                }
                e.printStackTrace()
            }
        }
    }

    private fun updateCourse(
        course: Courses,
        categories: List<GradeCategories>,
        scales: List<GradeScale>
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                coursesRepository.updateCourse(course)
                
                val categoriesToDelete = categoriesRepository.getCategoriesForCourse(course.id)
                val scalesToDelete = scalesRepository.getGradeScalesForCourse(course.id)

                categoriesRepository.deleteCategories(categoriesToDelete)
                scalesRepository.deleteGradeScales(scalesToDelete)

                val categoriesWithId = categories.map { it.copy(courseId = course.id) }
                categoriesRepository.addCategories(categoriesWithId)

                val scalesWithId = scales.map { it.copy(courseId = course.id) }
                scalesRepository.insertGradeScales(scalesWithId)

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = null
                    )
                }

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

    private fun refreshAssignment() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val currentSelectedCourseId = _state.value.selectedCourse?.course?.id
                val fetchedCourses = coursesRepository.getCourses()

                val updatedSelectedCourse = if (currentSelectedCourseId != null) {
                    coursesRepository.getCourseWithFullDetails(currentSelectedCourseId)
                } else null

                _state.update {
                    it.copy(
                        courses = fetchedCourses,
                        selectedCourse = updatedSelectedCourse,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to refresh: ${e.localizedMessage ?: "Unknown error"}"
                    )
                }
                e.printStackTrace()
            }
        }
    }
}