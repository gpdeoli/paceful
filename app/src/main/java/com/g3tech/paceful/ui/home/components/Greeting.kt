package com.g3tech.paceful.ui.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import java.time.LocalDateTime
import java.time.Month

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Greeting(
    dateTime: LocalDateTime = LocalDateTime.now(),
    hPadding : Dp = 0.dp,
    vPadding : Dp = 0.dp
) {
    val greeting : String =
        when (dateTime.month) {
            Month.DECEMBER if dateTime.dayOfMonth == 24 -> {
                stringResource(R.string.christmas_eve_greetings)
            }
            Month.DECEMBER if dateTime.dayOfMonth == 25 -> {
                stringResource(R.string.christmas_greetings)
            }
            Month.DECEMBER if dateTime.dayOfMonth == 31 -> {
                stringResource(R.string.new_year_greetings)
            }
            else -> {
                when (dateTime.hour) {
                    in 0..6 -> {
                        stringResource(R.string.dawn_greetings)
                    }
                    in 7..11 -> {
                        stringResource(R.string.morning_greetings)
                    }
                    in 12..19 -> {
                        stringResource(R.string.afternoon_greetings)
                    }
                    else -> {
                        stringResource(R.string.evening_greetings)
                    }
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