package com.example.doicram.db.repo

import com.example.doicram.db.dao.CoursesDao
import com.example.doicram.db.dao.GradeScaleDao
import com.example.doicram.db.entities.CourseWithCategories
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

    override suspend fun getCourses(): List<CourseWithCategories> {
        return coursesDao.getCourses()
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