package com.example.doicram.courses.db.repo

import com.example.doicram.courses.db.dao.CoursesDao
import com.example.doicram.courses.db.entities.CourseWithCategories
import com.example.doicram.courses.db.entities.CourseWithFullDetails
import com.example.doicram.courses.db.entities.Courses
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val coursesDao: CoursesDao
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
}