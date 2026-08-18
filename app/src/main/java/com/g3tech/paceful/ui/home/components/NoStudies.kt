package com.g3tech.paceful.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.ui.home.HomeScreenEvent
import com.g3tech.paceful.ui.theme.AppTheme

@Composable
fun NoStudies(
    innerPadding: PaddingValues,
    areThereSubjects: Boolean,
    onEvent: (HomeScreenEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val onPrimaryHalfOpacity = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        Icon(
            modifier = Modifier.size(100.dp),
            imageVector = ImageVector.vectorResource(R.drawable.subject_24),
            contentDescription = null,
            tint = onPrimaryHalfOpacity
        )
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(R.string.no_studies_description),
            color = onPrimaryHalfOpacity
        )
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable(onClick = { onEvent(HomeScreenEvent.NewStudyClick) })) {
            Box(
                modifier = Modifier.background(
                    color = Color.Unspecified,
                    shape = RoundedCornerShape(corner = CornerSize(12.dp))
                )
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.add_24),
                    contentDescription = "add symbol",
                    tint = onPrimaryHalfOpacity
                )
            }
            Spacer(Modifier.width(4.dp))
            Text(stringResource(R.string.add_study_if_none), color = onPrimaryHalfOpacity)
        }
        if (!areThereSubjects) {
            Text(
                text = stringResource(R.string.or),
                textAlign = TextAlign.Center,
                color = onPrimaryHalfOpacity
            )
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable(onClick = { onEvent(HomeScreenEvent.NewSubjectClick) })) {
                Box(
                    modifier = Modifier.background(
                        color = Color.Unspecified,
                        shape = RoundedCornerShape(12.dp)
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.add_24),
                        contentDescription = "add symbol",
                        tint = onPrimaryHalfOpacity
                    )
                }
                Spacer(Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.add_subject_if_none),
                    textAlign = TextAlign.Center,
                    color = onPrimaryHalfOpacity
                )
            }
        }
    }
}

@Preview(locale = "en")
@Composable
fun NoStudiesPreview() {
    AppTheme {
        NoStudies(innerPadding = PaddingValues(0.dp), areThereSubjects = false, onEvent = {})
    }
}