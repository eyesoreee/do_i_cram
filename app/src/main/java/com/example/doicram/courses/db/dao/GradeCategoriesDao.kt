package com.example.doicram.courses.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.doicram.courses.db.entities.GradeCategories

@Dao
interface GradeCategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(category: GradeCategories)

    @Query("SELECT * FROM grade_categories WHERE course_id = :courseId")
    suspend fun getCategoriesForCourse(courseId: Int): List<GradeCategories>

    @Delete
    suspend fun deleteCategory(category: GradeCategories)
}