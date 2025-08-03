package com.example.doicram.courses

import com.example.doicram.courses.db.repo.AssignmentsRepository
import com.example.doicram.courses.db.repo.CoursesRepository
import com.example.doicram.courses.db.repo.GradeCategoriesRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.round

@Singleton
class GradeCalculationService @Inject constructor(
    private val assignmentsRepository: AssignmentsRepository,
    private val categoriesRepository: GradeCategoriesRepository,
    private val coursesRepository: CoursesRepository
) {
    suspend fun recalculateGradesForCategory(categoryId: Int) {
        val category = categoriesRepository.getCategoryById(categoryId) ?: return

        val newCategoryGrade = calculateCategoryGrade(categoryId)

        if (category.currentGrade != newCategoryGrade) {
            val updatedCategory = category.copy(
                currentGrade = newCategoryGrade,
                updatedAt = System.currentTimeMillis()
            )
            categoriesRepository.updateCategory(updatedCategory)
        }

        recalculateGradeForCourse(category.courseId)
    }

    suspend fun recalculateGradeForCourse(courseId: Int) {
        val course = coursesRepository.getCourseById(courseId).course
        val newCourseGrade = calculateCourseGrade(courseId)

        if (course.grade != newCourseGrade) {
            val updatedCourse = course.copy(
                grade = newCourseGrade,
                updatedAt = System.currentTimeMillis()
            )
            coursesRepository.updateCourse(updatedCourse)
        }
    }

    private suspend fun calculateCategoryGrade(categoryId: Int): Double? {
        val assignments = assignmentsRepository.getAssignmentsForCategory(categoryId)

        val gradedAssignments = assignments.filter { it.score != null }

        if (gradedAssignments.isEmpty()) {
            return null
        }

        val totalScore = gradedAssignments.sumOf { it.score!!.toDouble() }
        val totalMaxScore = gradedAssignments.sumOf { it.maxScore }

        return if (totalMaxScore > 0) {
            val grade = (totalScore / totalMaxScore.toDouble()) * 100.0
            round(grade * 100.0) / 100.0
        } else {
            null
        }
    }


    private suspend fun calculateCourseGrade(courseId: Int): Double? {
        val categories = categoriesRepository.getCategoriesForCourse(courseId)

        val gradedCategories = categories.filter { it.currentGrade != null }

        if (gradedCategories.isEmpty()) {
            return null
        }

        val totalWeightedScore = gradedCategories.sumOf { it.currentGrade!! * it.weight }
        val totalWeight = gradedCategories.sumOf { it.weight }

        return if (totalWeight > 0) {
            val grade = totalWeightedScore / totalWeight
            round(grade * 100.0) / 100.0
        } else {
            null
        }
    }

    suspend fun recalculateAllGrades() {
        val courses = coursesRepository.getCourses()

        for (courseWithCategories in courses) {
            for (category in courseWithCategories.categories) {
                val newCategoryGrade = calculateCategoryGrade(category.id)
                if (category.currentGrade != newCategoryGrade) {
                    val updatedCategory = category.copy(
                        currentGrade = newCategoryGrade,
                        updatedAt = System.currentTimeMillis()
                    )
                    categoriesRepository.updateCategory(updatedCategory)
                }
            }

            recalculateGradeForCourse(courseWithCategories.course.id)
        }
    }
}