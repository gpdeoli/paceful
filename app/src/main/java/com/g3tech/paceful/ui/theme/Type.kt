package com.g3tech.paceful.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.g3tech.paceful.R

private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage   = "com.google.android.gms",
    certificates      = R.array.com_google_android_gms_fonts_certs
)

private val plusJakartaSansRaw = GoogleFont("Plus Jakarta Sans")
private val beVietnamProRaw    = GoogleFont("Be Vietnam Pro")

val PlusJakartaSans = FontFamily(Font(googleFont = plusJakartaSansRaw, fontProvider = provider))
val BeVietnamPro    = FontFamily(Font(googleFont = beVietnamProRaw, fontProvider = provider))

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily    = PlusJakartaSans,
        fontWeight    = FontWeight.Normal,
        fontSize      = 57.sp,
        lineHeight    = 64.sp,
        letterSpacing = (-1.5).sp,
    ),
    displayMedium = TextStyle(
        fontFamily    = PlusJakartaSans,
        fontWeight    = FontWeight.Normal,
        fontSize      = 48.sp,
        lineHeight    = 52.sp,
        letterSpacing = (-2.4).sp,
    ),
    displaySmall = TextStyle(
        fontFamily    = PlusJakartaSans,
        fontWeight    = FontWeight.Normal,
        fontSize      = 36.sp,
        lineHeight    = 44.sp,
        letterSpacing = (-0.9).sp,
    ),
    headlineLarge = TextStyle(
        fontFamily    = PlusJakartaSans,
        fontWeight    = FontWeight.Normal,
        fontSize      = 30.sp,
        lineHeight    = 36.sp,
        letterSpacing = (-0.75).sp,
    ),
    headlineMedium = TextStyle(
        fontFamily    = PlusJakartaSans,
        fontWeight    = FontWeight.Normal,
        fontSize      = 24.sp,
        lineHeight    = 32.sp,
        letterSpacing = (-0.6).sp,
    ),
    headlineSmall = TextStyle(
        fontFamily    = PlusJakartaSans,
        fontWeight    = FontWeight.Normal,
        fontSize      = 20.sp,
        lineHeight    = 28.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.Normal,
        fontSize   = 18.sp,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontFamily    = PlusJakartaSans,
        fontWeight    = FontWeight.Normal,
        fontSize      = 16.sp,
        lineHeight    = 24.sp,
        letterSpacing = 0.15.sp,
    ),
    titleSmall = TextStyle(
        fontFamily    = PlusJakartaSans,
        fontWeight    = FontWeight.Normal,
        fontSize      = 14.sp,
        lineHeight    = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = BeVietnamPro,
        fontWeight = FontWeight.Normal,
        fontSize   = 18.sp,
        lineHeight = 28.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = BeVietnamPro,
        fontWeight = FontWeight.Normal,
        fontSize   = 14.sp,
        lineHeight = 20.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = BeVietnamPro,
        fontWeight = FontWeight.Normal,
        fontSize   = 12.sp,
        lineHeight = 16.sp,
    ),
    labelLarge = TextStyle(
        fontFamily    = PlusJakartaSans,
        fontWeight    = FontWeight.ExtraBold,
        fontSize      = 12.sp,
        lineHeight    = 16.sp,
        letterSpacing = 0.6.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.Normal,
        fontSize   = 12.sp,
        lineHeight = 16.sp,
    ),
    labelSmall = TextStyle(
        fontFamily    = PlusJakartaSans,
        fontWeight    = FontWeight.Normal,
        fontSize      = 10.sp,
        lineHeight    = 15.sp,
        letterSpacing = 1.sp,
    ),
)
