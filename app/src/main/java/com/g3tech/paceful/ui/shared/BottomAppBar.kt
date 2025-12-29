package com.g3tech.paceful.ui.shared

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.g3tech.paceful.R

@Composable
fun BottomAppBar() {
    NavigationBar {
        NavigationBarItem(
            onClick = {},
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.home_24),
                    contentDescription = "home button"
                )
            },
            label = { Text(stringResource(R.string.home)) },
            selected = true
        )
        NavigationBarItem(
            onClick = {},
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.study_24),
                    contentDescription = "studies button"
                )
            },
            label = { Text(stringResource(R.string.studies)) },
            selected = false
        )
        NavigationBarItem(
            onClick = {},
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.subject_24),
                    contentDescription = "subjects button"
                )
            },
            label =  { Text(stringResource(R.string.subjects)) },
            selected = false
        )
        NavigationBarItem(
            onClick = {},
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.settings_24),
                    contentDescription = "settings button"
                )
            },
            label = { Text(stringResource(R.string.settings)) },
            selected = false
        )
    }
}