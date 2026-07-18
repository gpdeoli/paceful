package com.g3tech.paceful.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.ui.theme.AppTheme

@Composable
fun BottomAppBar(
    selectedIndex: Int = 0,
    onItemSelected: (Int) -> Unit = {},
) {
    val items = listOf(
        Pair(R.drawable.home_24, R.string.home),
        Pair(R.drawable.study_24, R.string.studies),
        Pair(R.drawable.subject_24, R.string.subjects),
        Pair(R.drawable.settings_24, R.string.settings),
    )

    val isDark = isSystemInDarkTheme()
    val activePillColor = if (isDark)
        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    else
        MaterialTheme.colorScheme.tertiary
    val activeContent = if (isDark)
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.onBackground
    val inactiveContent = if (isDark)
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
    else
        MaterialTheme.colorScheme.onSurfaceVariant

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items.forEachIndexed { index, (iconRes, labelRes) ->
                NavItem(
                    iconRes = iconRes,
                    labelRes = labelRes,
                    isSelected = index == selectedIndex,
                    activePillColor = activePillColor,
                    activeContent = activeContent,
                    inactiveContent = inactiveContent,
                    onClick = { onItemSelected(index) },
                )
            }
        }
    }
}

@Composable
private fun NavItem(
    iconRes: Int,
    labelRes: Int,
    isSelected: Boolean,
    activePillColor: Color,
    activeContent: Color,
    inactiveContent: Color,
    onClick: () -> Unit,
) {
    val contentColor = if (isSelected) activeContent else inactiveContent
    val pillModifier = if (isSelected)
        Modifier
            .background(color = activePillColor, shape = CircleShape)
            .padding(horizontal = 20.dp, vertical = 8.dp)
    else
        Modifier.padding(horizontal = 16.dp, vertical = 8.dp)

    Box(
        modifier = Modifier.clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = pillModifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(iconRes),
                contentDescription = null,
                tint = contentColor,
            )
            Text(
                text = stringResource(labelRes),
                style = MaterialTheme.typography.labelMedium,
                color = contentColor,
            )
        }
    }
}

@Preview
@Composable
fun BottomAppBarPreview() {
    AppTheme {
        BottomAppBar()
    }
}
