package com.example.doicram.courses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CourseTabButton(
    onClick: () -> Unit,
    isSelected: Boolean = false,
    icon: ImageVector = Icons.AutoMirrored.Filled.MenuBook,
    text: String,
    courseGrade: String? = null
) {
    val colorScheme = MaterialTheme.colorScheme

    val containerColor = if (isSelected) {
        colorScheme.primaryContainer
    } else {
        colorScheme.surfaceContainerHighest
    }

    val contentColor = if (isSelected) {
        colorScheme.onPrimaryContainer
    } else {
        colorScheme.onSurface
    }

    TextButton(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(text = text)

        if (courseGrade != null) {
            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = courseGrade,
                modifier = Modifier
                    .background(
                        if (isSelected) colorScheme.surface else colorScheme.surfaceContainer,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                color = if (isSelected) colorScheme.onSurface else colorScheme.onSurfaceVariant
            )
        }
    }
}