package com.example.doicram.courses.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.doicram.courses.db.entities.CourseWithCategories
import com.example.doicram.courses.db.entities.CourseWithFullDetails
import com.example.doicram.courses.db.entities.Courses

@Dao
interface CoursesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCourse(course: Courses): Long

    @Transaction
    @Query("SELECT * FROM courses")
    suspend fun getCourses(): List<CourseWithCategories>

    @Transaction
    @Query("SELECT * FROM courses WHERE id = :courseId")
    suspend fun getCourseById(courseId: Int): CourseWithCategories

    // New method for getting full details
    @Transaction
    @Query("SELECT * FROM courses WHERE id = :courseId")
    suspend fun getCourseWithFullDetails(courseId: Int): CourseWithFullDetails

    @Delete
    suspend fun deleteCourse(course: Courses)
}