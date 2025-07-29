package com.example.doicram.courses.db.repo

import com.example.doicram.courses.db.dao.CoursesDao
import com.example.doicram.courses.db.entities.Courses
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val coursesDao: CoursesDao
) : CoursesRepository {

    override suspend fun addCourse(course: Courses): Long {
        return coursesDao.addCourse(course)
    }

    override suspend fun getCourses(): List<Courses> {
        return coursesDao.getCourses()
    }

    override suspend fun deleteCourse(course: Courses) {
        coursesDao.deleteCourse(course)
    }
}