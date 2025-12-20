package com.g3tech.paceful.ui.home.components

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
    hPadding : Dp = 0.dp,
    vPadding : Dp = 0.dp
) {
    val greeting : String =
        when (dateTime.month) {
            Month.DECEMBER if dateTime.dayOfMonth == 25 -> {
                "Feliz Natal"

            }
            Month.DECEMBER if dateTime.dayOfMonth == 31 -> {
                "Feliz ano novo"
            }
            else -> {
                when (dateTime.hour) {
                    in 0..11 -> {
                        "Bom dia"
                    }

                    in 12..19 -> {
                        "Boa tarde"
                    }

                    else -> "Boa noite"
                }
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