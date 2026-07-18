package com.g3tech.paceful.ui.shared

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.g3tech.paceful.R

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FabMenu(items: List<FabItem>) {
    var expanded by remember { mutableStateOf(false) }
    val primaryColor = MaterialTheme.colorScheme.primary
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
    val surfaceVariantColor = MaterialTheme.colorScheme.surfaceVariant
    val onSurfaceVariantColor = MaterialTheme.colorScheme.onSurfaceVariant

    FloatingActionButtonMenu(
        expanded = expanded,
        button = {
            ToggleFloatingActionButton(
                checked = expanded,
                onCheckedChange = { expanded = it },
                containerColor = { primaryColor },
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        if (expanded) R.drawable.close_24 else R.drawable.add_24
                    ),
                    contentDescription = if (expanded) "Close" else "Add",
                    tint = onPrimaryColor,
                )
            }
        },
    ) {
        items.forEach { item ->
            FloatingActionButtonMenuItem(
                onClick = {
                    item.onClick()
                    expanded = false
                },
                containerColor = surfaceVariantColor,
                contentColor = onSurfaceVariantColor,
                text = { Text(item.label) },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(item.icon),
                        contentDescription = item.label
                    )
                },
            )
        }
    }
}

data class FabItem(val icon: Int, val label: String, val onClick: () -> Unit)
