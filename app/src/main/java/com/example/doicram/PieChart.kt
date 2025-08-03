package com.example.doicram

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

@Immutable
/**
 * Data class representing a pie segment with color, value, and label.
 */
data class ChartData(
    val color: Color,
    val data: Float,
    val label: String
)

private val Float.degreeToRadians: Float
    get() = (this * Math.PI / 180f).toFloat()

@Composable
fun PieChart(
    chartDataList: List<ChartData>,
    modifier: Modifier = Modifier,
    sizeFraction: Float = 0.7f,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth(sizeFraction)
                .aspectRatio(1f)
        ) {
            val total = chartDataList.sumOf { it.data.toDouble() }.toFloat().takeIf { it > 0f }
                ?: return@Canvas
            val width = size.width

            var startAngle = -90f
            chartDataList.forEach { chartData ->
                val sweepAngle = (chartData.data / total) * 360f
                val midAngleRad = (startAngle + sweepAngle / 2).degreeToRadians

                // Draw filled slice
                drawArc(
                    color = chartData.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    topLeft = Offset.Zero,
                    size = Size(width, width)
                )

                startAngle += sweepAngle
            }
        }
    }
}

