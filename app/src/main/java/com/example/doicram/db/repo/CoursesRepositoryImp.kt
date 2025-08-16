package com.example.doicram.db.repo

import com.example.doicram.dashboard.ActiveCoursesInfo
import com.example.doicram.dashboard.NeedAttentionInfo
import com.example.doicram.db.dao.CoursesDao
import com.example.doicram.db.dao.GradeScaleDao
import com.example.doicram.db.entities.CourseWithCategories
import com.example.doicram.db.entities.CourseWithCategoryAndScale
import com.example.doicram.db.entities.CourseWithFullDetails
import com.example.doicram.db.entities.Courses
import com.example.doicram.db.entities.GradeScale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val coursesDao: CoursesDao,
    private val gradeScaleDao: GradeScaleDao
) : CoursesRepository {

    override suspend fun addCourse(course: Courses): Long {
        return coursesDao.addCourse(course)
    }

    override suspend fun getCourses(showArchived: Boolean): List<CourseWithCategoryAndScale> {
        val coursesWithCounts = coursesDao.getCourseWithAssignmentCounts(showArchived)

        return coursesWithCounts.map { course ->
            val categories = coursesDao.getCategoriesForCourse(course.course.id)
            val scales = coursesDao.getGradeScalesForCourse(course.course.id)

            CourseWithCategoryAndScale(
                course = course.course,
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

    override suspend fun getActiveCourses(): List<Courses> {
        return coursesDao.getActiveCourses()
    }

    override fun getCGPA(): Flow<Double?> {
        return coursesDao.getActiveCoursesWithScaleFlow()
            .map { activeCourses ->
                val totalUnits = activeCourses.sumOf { it.course.units }

                val totalGradePoints = activeCourses.sumOf { cw ->
                    val gpa = cw.course.grade
                        ?.let { GradeScale.calculateGpa(it, cw.gradeScales) }
                        ?: 0.0
                    gpa * cw.course.units
                }

                if (totalUnits > 0) totalGradePoints / totalUnits else null
            }
    }

    override suspend fun getActiveCoursesInfo(): ActiveCoursesInfo {
        return coursesDao.getActiveCoursesInfo()
    }

    override fun getActiveCoursesInfoFlow(): Flow<ActiveCoursesInfo> {
        return coursesDao.getActiveCoursesInfoFlow()
    }

    override suspend fun getNeedAttentionInfo(): NeedAttentionInfo {
        return coursesDao.getNeedAttentionInfo()
    }

    override fun getNeedAttentionInfoFlow(): Flow<NeedAttentionInfo> {
        return coursesDao.getNeedAttentionInfoFlow()
    }

}