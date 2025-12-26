package com.g3tech.paceful.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun CardStats(modifier: Modifier = Modifier, title: String, value: Int, icon: Int) {
    Card(modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(horizontal = 6.dp)) {
                Text(title, style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = formatNumber(value), style = MaterialTheme.typography.titleLarge)
            }
            Icon(
                modifier = Modifier.padding(horizontal = 6.dp),
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = "scheduled"
            )
        }
    }
}

fun formatNumber(value: Int) : String {
    return if (value >= 1000000) {
        val millions = value / 1000
        return "${millions}M"
    } else if (value >= 1000) {
        val thousands = value / 1000
        return "${thousands}K"
    } else {
        value.toString()
    }
}
