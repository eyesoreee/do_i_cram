package com.example.doicram.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun OverviewCard(
    title: String,
    icon: ImageVector,
    iconColor: Color,
    mainValue: String,
    mainValueColor: Color,
    secondaryValue: String,
    bottomIcon: ImageVector,
    bottomIconColor: Color = Color.Unspecified,
    bottomText: String,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Card(
        modifier = modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceContainer)
    ) {
        Column(
            Modifier
                .padding(24.dp)
                .fillMaxHeight()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, style = typography.bodyLarge)
                Icon(
                    icon,
                    contentDescription = null,
                    tint = iconColor
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = mainValue,
                    style = typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = mainValueColor,
                    modifier = Modifier.alignBy(FirstBaseline)
                )

                Text(
                    text = secondaryValue,
                    style = typography.bodyLarge,
                    color = colorScheme.onSurfaceVariant.copy(alpha = 0.9f),
                    modifier = Modifier.alignBy(FirstBaseline)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = bottomIcon,
                    contentDescription = null,
                    tint = if (bottomIconColor == Color.Unspecified) colorScheme.onSurfaceVariant.copy(
                        alpha = 0.9f
                    ) else bottomIconColor,
                    modifier = Modifier.size(12.dp)
                )

                Text(
                    text = bottomText,
                    style = typography.bodySmall,
                    color = colorScheme.onSurfaceVariant.copy(alpha = 0.9f),
                )
            }
        }
    }
}