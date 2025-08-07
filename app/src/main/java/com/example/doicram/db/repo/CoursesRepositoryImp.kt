package com.example.doicram.db.repo

import com.example.doicram.db.dao.CoursesDao
import com.example.doicram.db.dao.GradeScaleDao
import com.example.doicram.db.entities.CourseWithCategories
import com.example.doicram.db.entities.CourseWithCategoryAndScale
import com.example.doicram.db.entities.CourseWithFullDetails
import com.example.doicram.db.entities.Courses
import com.example.doicram.db.entities.GradeScale
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val coursesDao: CoursesDao,
    private val gradeScaleDao: GradeScaleDao
) : CoursesRepository {

    override suspend fun addCourse(course: Courses): Long {
        return coursesDao.addCourse(course)
    }

    override suspend fun getCourses(): List<CourseWithCategoryAndScale> {
        val coursesWithCounts = coursesDao.getCourseWithAssignmentCounts()

        return coursesWithCounts.map { course ->
            val categories = coursesDao.getCategoriesForCourse(course.courses.id)
            val scales = coursesDao.getGradeScalesForCourse(course.courses.id)

            CourseWithCategoryAndScale(
                course = course.courses,
                categories = categories,
                scales = scales,
                totalAssignments = course.totalAssignments,
                completedAssignments = course.completedAssignments
            )
        }
    }

    override suspend fun getCourseById(courseId: Int): CourseWithCategories {
        return coursesDao.getCourseById(courseId)
    }

    override suspend fun getCourseWithFullDetails(courseId: Int): CourseWithFullDetails {
        return coursesDao.getCourseWithFullDetails(courseId)
    }

    override suspend fun deleteCourse(course: Courses) {
        coursesDao.deleteCourse(course)
    }

    override suspend fun updateCourse(course: Courses) {
        coursesDao.updateCourse(course)
    }

    override suspend fun getGradingScaleForCourse(courseId: Int): List<GradeScale> {
        val customScales = gradeScaleDao.getByCourseId(courseId)
        return customScales.ifEmpty {
            gradeScaleDao.getDefaultScales()
        }
    }
}