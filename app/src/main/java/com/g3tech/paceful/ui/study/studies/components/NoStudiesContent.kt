package com.g3tech.paceful.ui.study.studies.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.ui.study.studies.StudiesEvent

@Composable
fun NoStudiesContent(
    hasActiveFilter: Boolean,
    onEvent: (StudiesEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val mutedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.size(80.dp),
            imageVector = ImageVector.vectorResource(R.drawable.study_24),
            contentDescription = null,
            tint = mutedColor,
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = if (hasActiveFilter) stringResource(R.string.no_studies_in_section)
                   else stringResource(R.string.no_studies_description),
            style = MaterialTheme.typography.bodyLarge,
            color = mutedColor,
            textAlign = TextAlign.Center,
        )

        if (!hasActiveFilter) {
            Spacer(Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onEvent(StudiesEvent.NewStudyClick) },
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.add_24),
                    contentDescription = null,
                    tint = mutedColor,
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.add_study_if_none),
                    color = mutedColor,
                )
            }
        }
    }
}
