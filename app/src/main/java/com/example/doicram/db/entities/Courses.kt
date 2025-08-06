package com.example.doicram.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "courses",
    foreignKeys = [
        ForeignKey(
            entity = GradeScale::class,
            parentColumns = ["id"],
            childColumns = ["grade_scale_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index(value = ["grade_scale_id"])]
)
data class Courses(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "units")
    val units: Int,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "target_grade")
    val targetGrade: Double? = null,

    @ColumnInfo(name = "grade")
    val grade: Double? = null,

    @ColumnInfo(name = "grade_scale_id")
    val gradeScaleId: Int? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "archived_at")
    val archivedAt: Long? = null
)