package com.example.doicram.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.doicram.dashboard.PendingAssignmentsInfo
import com.example.doicram.db.entities.Assignments
import kotlinx.coroutines.flow.Flow

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

    @Query(
        """
        SELECT 
            COALESCE(COUNT(CASE WHEN a.score IS NULL THEN 1 END), 0) as totalCount,
            COUNT(CASE WHEN a.score IS NOT NULL THEN 1 END) as completed
        FROM assignments a
        INNER JOIN grade_categories gc ON a.category_id = gc.id
        INNER JOIN courses c ON gc.course_id = c.id
        WHERE a.archived_at IS NULL 
        AND c.archived_at IS NULL
    """
    )
    suspend fun getPendingAssignmentsInfo(): PendingAssignmentsInfo

    @Query(
        """
        SELECT 
            COALESCE(COUNT(CASE WHEN a.score IS NULL THEN 1 END), 0) as totalCount,
            COUNT(CASE WHEN a.score IS NOT NULL THEN 1 END) as completed
        FROM assignments a
        INNER JOIN grade_categories gc ON a.category_id = gc.id
        INNER JOIN courses c ON gc.course_id = c.id
        WHERE a.archived_at IS NULL 
        AND c.archived_at IS NULL
    """
    )
    fun getPendingAssignmentsInfoFlow(): Flow<PendingAssignmentsInfo>
}