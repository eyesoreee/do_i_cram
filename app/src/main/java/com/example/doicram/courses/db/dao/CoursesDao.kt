package com.example.doicram.courses.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.doicram.courses.db.entities.Courses

@Dao
interface CoursesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCourse(course: Courses)

    @Query("SELECT * FROM courses")
    suspend fun getAllCourses(): List<Courses>

    @Delete
    suspend fun deleteCourse(course: Courses)

}