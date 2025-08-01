package com.example.doicram.courses

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.doicram.courses.db.entities.CourseWithFullDetails

@Composable
fun DetailedCourse(course: CourseWithFullDetails) {
    Text(text = "Selected Course: ${course.course.code} ${course.course.name}")
}