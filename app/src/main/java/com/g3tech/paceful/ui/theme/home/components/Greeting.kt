package com.g3tech.paceful.ui.theme.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.Month

@Composable
fun Greeting(
    dateTime: LocalDateTime = LocalDateTime.now(),
    name : String,
    hPadding : Dp = 0.dp,
    vPadding : Dp = 0.dp
) {
    val greeting : String =
        if (dateTime.month == Month.DECEMBER && dateTime.dayOfMonth == 25 ) {
            "Feliz Natal, $name"

        } else if (dateTime.month == Month.DECEMBER && dateTime.dayOfMonth == 31) {
            "Feliz ano novo, $name"
        } else {
            when (dateTime.hour) {
                in 0..11 -> {
                    "Bom dia, $name"
                }

                in 12..19 -> {
                    "Boa tarde, $name"
                }

                else -> "Boa noite, $name"
            }
        }
    Box(modifier = Modifier.padding(vertical = vPadding, horizontal = hPadding)) {
        Text(
            text = greeting,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}