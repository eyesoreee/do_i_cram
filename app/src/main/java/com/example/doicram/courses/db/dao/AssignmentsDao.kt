package com.example.doicram.courses.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.doicram.courses.db.entities.Assignments

@Dao
interface AssignmentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAssignment(assignment: Assignments): Long

    @Update
    suspend fun updateAssignment(assignment: Assignments)

    @Query("SELECT * FROM assignments WHERE category_id = :categoryId ORDER BY due_date ASC")
    suspend fun getAssignmentsForCategory(categoryId: Int): List<Assignments>

    @Query("SELECT * FROM assignments WHERE id = :assignmentId")
    suspend fun getAssignmentById(assignmentId: Int): Assignments?

    @Delete
    suspend fun deleteAssignment(assignment: Assignments)

    @Query("DELETE FROM assignments WHERE category_id = :categoryId")
    suspend fun deleteAssignmentsForCategory(categoryId: Int)
}