package com.example.doicram.db.repo

import com.example.doicram.db.entities.CourseWithCategories
import com.example.doicram.db.entities.CourseWithFullDetails
import com.example.doicram.db.entities.Courses
import com.example.doicram.db.entities.GradeScale

interface CoursesRepository {
    suspend fun addCourse(course: Courses): Long
    suspend fun getCourses(): List<CourseWithCategories>
    suspend fun getCourseById(courseId: Int): CourseWithCategories
    suspend fun getCourseWithFullDetails(courseId: Int): CourseWithFullDetails
    suspend fun deleteCourse(course: Courses)
    suspend fun updateCourse(course: Courses)
    suspend fun getGradingScaleForCourse(courseId: Int): List<GradeScale>
}