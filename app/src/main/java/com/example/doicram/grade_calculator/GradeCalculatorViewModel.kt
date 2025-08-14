package com.example.doicram.grade_calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doicram.db.entities.CategoryColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GradeCalculatorViewModel : ViewModel() {
    private val _state = MutableStateFlow(GradeCalculatorState())
    val state: StateFlow<GradeCalculatorState> = _state.asStateFlow()

    init {
        initializeDefaultData()
    }

    private fun initializeDefaultData() {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    categories = getDefaultCategories(),
                    scales = getDefaultGpaScales()
                )
            }
        }
    }

    fun onAction(action: GradeCalculatorAction) {
        when (action) {
            is GradeCalculatorAction.OnAddCourseClick -> {
                addCourse(action.course)
            }

            is GradeCalculatorAction.OnDeleteCourseClick -> {
                deleteCourse(action.course)
            }

            is GradeCalculatorAction.OnAddAssignmentClick -> {
                addAssignment(action.assignment)
            }

            is GradeCalculatorAction.OnDeleteAssignmentClick -> {
                deleteAssignment(action.assignment)
            }

            is GradeCalculatorAction.OnEditClick -> {
                editCategoriesAndScales(action.categories, action.scales)
            }
        }
    }

    private fun addCourse(course: CalculatorCourse) {
        _state.update { currentState ->
            val newCourses = currentState.courses + course
            currentState.copy(
                courses = newCourses,
                totalCourses = newCourses.size,
                totalUnits = calculateTotalUnits(newCourses),
                cgpa = calculateCgpa(newCourses)
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
                currentState.copy(
                    courses = newCourses,
                    totalCourses = newCourses.size,
                    totalUnits = calculateTotalUnits(newCourses),
                    cgpa = calculateCgpa(newCourses)
                )
            }
        }
    }

    private fun addAssignment(assignment: GpaAssignment) {
        _state.update { currentState ->
            val updatedCategories = updateCategoriesWithAssignment(
                categories = currentState.categories,
                assignment = assignment,
                isAdding = true
            )

            currentState.copy(
                categories = updatedCategories,
                gpa = calculateOverallGpa(updatedCategories, currentState.scales)
            )
        }
    }

    private fun deleteAssignment(assignment: GpaAssignment) {
        _state.update { currentState ->
            val updatedCategories = updateCategoriesWithAssignment(
                categories = currentState.categories,
                assignment = assignment,
                isAdding = false
            )

            currentState.copy(
                categories = updatedCategories,
                gpa = calculateOverallGpa(updatedCategories, currentState.scales)
            )
        }
    }

    private fun editCategoriesAndScales(
        newCategories: List<GpaCategory>,
        newScales: List<GpaScale>
    ) {
        _state.update { currentState ->
            val updatedCategories = preserveExistingAssignments(
                oldCategories = currentState.categories,
                newCategories = newCategories
            )

            val recalculatedCategories = recalculateAllCategoryGrades(updatedCategories)

            currentState.copy(
                categories = recalculatedCategories,
                scales = newScales,
                gpa = calculateOverallGpa(recalculatedCategories, newScales)
            )
        }
    }

    private fun calculateTotalUnits(courses: List<CalculatorCourse>): Int {
        return courses.sumOf { it.units }
    }

    private fun calculateCgpa(courses: List<CalculatorCourse>): Double? {
        if (courses.isEmpty()) return null

        val totalWeightedGpa = courses.sumOf { it.units * it.gpa }
        val totalUnits = courses.sumOf { it.units }

        return if (totalUnits > 0) totalWeightedGpa / totalUnits else null
    }

    private fun updateCategoriesWithAssignment(
        categories: List<GpaCategory>,
        assignment: GpaAssignment,
        isAdding: Boolean
    ): List<GpaCategory> {
        return categories.map { category ->
            if (category.name == assignment.category) {
                val updatedAssignments = if (isAdding) {
                    category.assignments + assignment
                } else {
                    category.assignments.filter { it != assignment }
                }

                category.copy(
                    assignments = updatedAssignments,
                    grade = calculateCategoryGrade(updatedAssignments)
                )
            } else {
                category
            }
        }
    }

    private fun preserveExistingAssignments(
        oldCategories: List<GpaCategory>,
        newCategories: List<GpaCategory>
    ): List<GpaCategory> {
        return newCategories.map { newCategory ->
            val matchingOldCategory = oldCategories.find { it.name == newCategory.name }

            if (matchingOldCategory != null)
                newCategory.copy(assignments = matchingOldCategory.assignments)
            else
                newCategory.copy(assignments = emptyList())

        }
    }

    private fun recalculateAllCategoryGrades(categories: List<GpaCategory>): List<GpaCategory> {
        return categories.map { category ->
            category.copy(grade = calculateCategoryGrade(category.assignments))
        }
    }

    private fun calculateCategoryGrade(assignments: List<GpaAssignment>): Double? {
        if (assignments.isEmpty()) return null

        val totalScore = assignments.sumOf { it.score }
        val totalMaxScore = assignments.sumOf { it.maxScore }

        return if (totalMaxScore > 0) {
            (totalScore.toDouble() / totalMaxScore.toDouble()) * 100
        } else {
            null
        }
    }

    private fun calculateOverallGrade(categories: List<GpaCategory>): Double? {
        val categoriesWithGrades = categories.filter { it.grade != null }

        if (categoriesWithGrades.isEmpty()) return null

        val totalWeightedGrade = categoriesWithGrades.sumOf { category ->
            category.grade!! * category.weight
        }
        val totalWeight = categoriesWithGrades.sumOf { it.weight }

        return if (totalWeight > 0) totalWeightedGrade / totalWeight else null
    }

    private fun calculateOverallGpa(
        categories: List<GpaCategory>,
        scales: List<GpaScale>
    ): Double? {
        val overallGrade = calculateOverallGrade(categories) ?: return null
        return convertGradeToGpa(overallGrade, scales)
    }

    private fun convertGradeToGpa(grade: Double, scales: List<GpaScale>): Double? {
        return scales.find { scale ->
            grade >= scale.minPercentage && grade <= scale.maxPercentage
        }?.gpaValue
    }

    // Default Data
    private fun getDefaultCategories(): List<GpaCategory> {
        return listOf(
            GpaCategory(
                name = "Prelim Exam",
                weight = 20.0,
                colorName = CategoryColor.BLUE
            ),
            GpaCategory(
                name = "Midterm Exam",
                weight = 20.0,
                colorName = CategoryColor.GREEN
            ),
            GpaCategory(
                name = "Final Exam",
                weight = 20.0,
                colorName = CategoryColor.RED
            ),
            GpaCategory(
                name = "Project",
                weight = 25.0,
                colorName = CategoryColor.PURPLE
            ),
            GpaCategory(
                name = "Activities and Quizzes",
                weight = 15.0,
                colorName = CategoryColor.PINK
            )
        )
    }

    private fun getDefaultGpaScales(): List<GpaScale> {
        return listOf(
            GpaScale(
                gpaValue = 1.00,
                minPercentage = 97.0,
                maxPercentage = 100.0,
                description = "Excellent",
                isPassing = true
            ),
            GpaScale(
                gpaValue = 1.25,
                minPercentage = 94.0,
                maxPercentage = 96.9,
                description = "Excellent",
                isPassing = true
            ),
            GpaScale(
                gpaValue = 1.50,
                minPercentage = 91.0,
                maxPercentage = 93.9,
                description = "Excellent",
                isPassing = true
            ),
            GpaScale(
                gpaValue = 1.75,
                minPercentage = 88.0,
                maxPercentage = 90.9,
                description = "Very Good",
                isPassing = true
            ),
            GpaScale(
                gpaValue = 2.00,
                minPercentage = 85.0,
                maxPercentage = 87.9,
                description = "Very Good",
                isPassing = true
            ),
            GpaScale(
                gpaValue = 2.25,
                minPercentage = 82.0,
                maxPercentage = 84.9,
                description = "Good",
                isPassing = true
            ),
            GpaScale(
                gpaValue = 2.50,
                minPercentage = 79.0,
                maxPercentage = 81.9,
                description = "Satisfactory",
                isPassing = true
            ),
            GpaScale(
                gpaValue = 2.75,
                minPercentage = 76.0,
                maxPercentage = 78.9,
                description = "Satisfactory",
                isPassing = true
            ),
            GpaScale(
                gpaValue = 3.00,
                minPercentage = 75.0,
                maxPercentage = 75.9,
                description = "Passing",
                isPassing = true
            ),
            GpaScale(
                gpaValue = 5.00,
                minPercentage = 0.0,
                maxPercentage = 74.9,
                description = "Failure",
                isPassing = false
            )
        )
    }
}