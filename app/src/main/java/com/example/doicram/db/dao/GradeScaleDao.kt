package com.example.doicram.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.doicram.db.entities.GradeScale

@Dao
interface GradeScaleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGradeScale(gradeScale: GradeScale): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGradeScales(gradeScales: List<GradeScale>): List<Long>

    @Update
    suspend fun updateGradeScale(gradeScale: GradeScale): Int

    @Delete
    suspend fun deleteGradeScale(gradeScale: GradeScale): Int

    @Delete
    suspend fun deleteGradeScales(gradeScales: List<GradeScale>)

    @Query("SELECT * FROM grade_scales ORDER BY min_percentage DESC")
    suspend fun getAllGradeScales(): List<GradeScale>

    @Query("SELECT * FROM grade_scales WHERE course_id = :courseId ORDER BY min_percentage DESC")
    suspend fun getByCourseId(courseId: Int): List<GradeScale>

    @Query("SELECT * FROM grade_scales WHERE course_id IS NULL AND is_default = 1 ORDER BY min_percentage DESC")
    suspend fun getDefaultScales(): List<GradeScale>

    @Query("SELECT * FROM grade_scales WHERE id = :id")
    suspend fun getGradeScaleById(id: Int): GradeScale?
}