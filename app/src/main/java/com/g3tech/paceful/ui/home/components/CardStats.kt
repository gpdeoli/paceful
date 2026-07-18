package com.g3tech.paceful.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.ui.theme.AppTheme
import com.g3tech.paceful.ui.theme.StatusColor

@Composable
fun CardStats(
    modifier: Modifier = Modifier,
    title: String,
    value: Int,
    icon: Int,
    statusColor: StatusColor,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        color = statusColor.container,
        shadowElevation = 0.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    color = statusColor.content,
                )
                Text(
                    text = formatNumber(value),
                    style = MaterialTheme.typography.titleLarge,
                    color = statusColor.content,
                )
            }
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                tint = statusColor.content,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

fun formatNumber(value: Int): String {
    return if (value >= 1000000) {
        val millions = value / 1000
        "${millions}M"
    } else if (value >= 1000) {
        val thousands = value / 1000
        "${thousands}K"
    } else {
        value.toString()
    }
}

@Composable
@Preview
fun CardStatsPreview() {
    AppTheme {
        CardStats(
            title = "Scheduled",
            value = 3,
            icon = 0,
            statusColor = StatusColor(
                container = MaterialTheme.colorScheme.surfaceVariant,
                content = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
        )
    }
}
