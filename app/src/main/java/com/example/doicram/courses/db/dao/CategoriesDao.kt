package com.example.doicram.courses.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.doicram.courses.db.entities.Courses
import com.example.doicram.courses.db.entities.GradeCategories

@Dao
interface CategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(category: GradeCategories)

    @Query("SELECT * FROM grade_categories")
    suspend fun getCategories(): List<Courses>

    @Delete
    suspend fun deleteCourse(course: Courses)

}