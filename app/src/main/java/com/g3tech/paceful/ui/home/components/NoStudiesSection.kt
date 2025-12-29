package com.g3tech.paceful.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R

@Composable
fun NoStudiesSection() {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val onPrimaryHalfOpacity = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
        Icon(
            modifier = Modifier.size(36.dp),
            imageVector = ImageVector.vectorResource(R.drawable.subject_24),
            contentDescription = null,
            tint = onPrimaryHalfOpacity
        )
        Text(
            stringResource(R.string.no_studies_in_section),
            color = onPrimaryHalfOpacity,
            style = MaterialTheme.typography.bodySmall
        )
    }
}