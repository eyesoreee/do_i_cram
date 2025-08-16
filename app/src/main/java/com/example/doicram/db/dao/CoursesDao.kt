package com.example.doicram.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.doicram.dashboard.ActiveCoursesInfo
import com.example.doicram.dashboard.NeedAttentionInfo
import com.example.doicram.db.entities.CourseWithAssignmentCounts
import com.example.doicram.db.entities.CourseWithCategories
import com.example.doicram.db.entities.CourseWithFullDetails
import com.example.doicram.db.entities.CourseWithGradeScale
import com.example.doicram.db.entities.Courses
import com.example.doicram.db.entities.GradeCategories
import com.example.doicram.db.entities.GradeScale
import kotlinx.coroutines.flow.Flow

@Dao
interface CoursesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCourse(course: Courses): Long

    @Transaction
    @Query("SELECT * FROM courses")
    suspend fun getCourses(): List<Courses>

    @Transaction
    @Query("SELECT * FROM courses WHERE id = :courseId")
    suspend fun getCourseById(courseId: Int): CourseWithCategories

    @Transaction
    @Query("SELECT * FROM courses WHERE id = :courseId")
    suspend fun getCourseWithFullDetails(courseId: Int): CourseWithFullDetails

    @Delete
    suspend fun deleteCourse(course: Courses)

    @Update
    suspend fun updateCourse(course: Courses)

    @Query(
        """
        UPDATE courses 
        SET 
            name = CASE WHEN :name IS NOT NULL THEN :name ELSE name END,
            code = CASE WHEN :code IS NOT NULL THEN :code ELSE code END,
            units = CASE WHEN :units IS NOT NULL THEN :units ELSE units END,
            description = CASE WHEN :description IS NOT NULL THEN :description ELSE description END,
            target_grade = CASE WHEN :targetGrade IS NOT NULL THEN :targetGrade ELSE target_grade END,
            grade = CASE WHEN :grade IS NOT NULL THEN :grade ELSE grade END,
            archived_at = CASE WHEN :archivedAt IS NOT NULL THEN :archivedAt ELSE archived_at END,
            updated_at = :updatedAt
        WHERE id = :id
    """
    )
    suspend fun updateCoursePartially(
        id: Int,
        name: String? = null,
        code: String? = null,
        units: Int? = null,
        description: String? = null,
        targetGrade: Double? = null,
        grade: Double? = null,
        archivedAt: Long? = null,
        updatedAt: Long = System.currentTimeMillis()
    )

    @Query(
        """
    SELECT course.*,
        COALESCE(COUNT(assignment.id), 0) as totalAssignments,
        COALESCE(SUM(CASE WHEN assignment.score IS NOT NULL THEN 1 ELSE 0 END), 0) as completedAssignments
    FROM courses AS course
    LEFT JOIN grade_categories AS category ON course.id = category.course_id
    LEFT JOIN assignments AS assignment ON category.id = assignment.category_id
    WHERE :showArchived = 1 OR course.archived_at IS NULL
    GROUP BY course.id
    ORDER BY course.created_at DESC
    """
    )
    suspend fun getCourseWithAssignmentCounts(showArchived: Boolean = false): List<CourseWithAssignmentCounts>

    @Query("SELECT * FROM grade_categories WHERE course_id = :courseId AND archived_at IS NULL")
    suspend fun getCategoriesForCourse(courseId: Int): List<GradeCategories>

    @Query("SELECT * FROM grade_scales WHERE course_id = :courseId")
    suspend fun getGradeScalesForCourse(courseId: Int): List<GradeScale>

    @Query("SELECT * FROM courses WHERE archived_at IS NULL")
    suspend fun getActiveCourses(): List<Courses>

    @Transaction
    @Query("SELECT * FROM courses WHERE archived_at IS NULL")
    suspend fun getActiveCoursesWithScale(): List<CourseWithGradeScale>

    @Transaction
    @Query("SELECT * FROM courses WHERE archived_at IS NULL")
    fun getActiveCoursesWithScaleFlow(): Flow<List<CourseWithGradeScale>>

    // For Dashboard
    @Query(
        """
        SELECT 
            COUNT(*) as count,
            COALESCE(SUM(units), 0) as totalUnits
        FROM courses 
        WHERE archived_at IS NULL
    """
    )
    suspend fun getActiveCoursesInfo(): ActiveCoursesInfo

    @Query(
        """
        SELECT 
            COUNT(*) as count,
            COALESCE(SUM(units), 0) as totalUnits
        FROM courses 
        WHERE archived_at IS NULL
    """
    )
    fun getActiveCoursesInfoFlow(): Flow<ActiveCoursesInfo>


    @Query(
        """
        SELECT COUNT(*) as count
        FROM courses 
        WHERE archived_at IS NULL
        AND target_grade IS NOT NULL 
        AND grade IS NOT NULL
        AND grade < target_grade
    """
    )
    suspend fun getNeedAttentionInfo(): NeedAttentionInfo

    @Query(
        """
        SELECT COUNT(*) as count
        FROM courses 
        WHERE archived_at IS NULL
        AND target_grade IS NOT NULL 
        AND grade IS NOT NULL
        AND grade < target_grade
    """
    )
    fun getNeedAttentionInfoFlow(): Flow<NeedAttentionInfo>
}