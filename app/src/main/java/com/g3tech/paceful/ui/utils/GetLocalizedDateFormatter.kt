package com.g3tech.paceful.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
public fun getLocalizedDateFormatter(): DateTimeFormatter {
    val locale = LocalConfiguration.current.locales[0]

    return remember(locale) {
        if (locale.language == "pt") {
            DateTimeFormatter.ofPattern("d MMM yyyy", locale)
        } else {
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(locale)
        }
    }
}