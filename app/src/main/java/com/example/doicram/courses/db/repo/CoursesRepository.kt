package com.example.doicram.courses.db.repo

import com.example.doicram.courses.db.entities.CourseWithCategories
import com.example.doicram.courses.db.entities.CourseWithFullDetails
import com.example.doicram.courses.db.entities.Courses

interface CoursesRepository {
    suspend fun addCourse(course: Courses): Long
    suspend fun getCourses(): List<CourseWithCategories>
    suspend fun getCourseById(courseId: Int): CourseWithCategories
    suspend fun getCourseWithFullDetails(courseId: Int): CourseWithFullDetails
    suspend fun deleteCourse(course: Courses)
    suspend fun updateCourse(course: Courses)
}