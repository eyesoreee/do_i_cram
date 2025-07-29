package com.example.doicram.courses.db.repo

import com.example.doicram.courses.db.entities.Courses

interface CoursesRepository {
    suspend fun addCourse(course: Courses): Long
    suspend fun getCourses(): List<Courses>
    suspend fun deleteCourse(course: Courses)
}