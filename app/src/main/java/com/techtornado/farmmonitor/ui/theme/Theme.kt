package com.techtornado.farmmonitor.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.techtornado.farmmonitor.R

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun FarmMonitorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography.copy(
            displayLarge = Typography.displayLarge.copy(fontFamily = FontFamily(Font(R.font.cookie_run_regular))),
            displayMedium = Typography.displayMedium.copy(fontFamily = FontFamily(Font(R.font.cookie_run_regular))),
            displaySmall = Typography.displaySmall.copy(fontFamily = FontFamily(Font(R.font.cookie_run_regular))),
            headlineLarge = Typography.headlineLarge.copy(fontFamily = FontFamily(Font(R.font.cookie_run_regular))),
            headlineMedium = Typography.headlineMedium.copy(fontFamily = FontFamily(Font(R.font.cookie_run_regular))),
            headlineSmall = Typography.headlineSmall.copy(fontFamily = FontFamily(Font(R.font.cookie_run_regular))),
            titleLarge = Typography.titleLarge.copy(fontFamily = FontFamily(Font(R.font.cookie_run_regular))),
            titleMedium = Typography.titleMedium.copy(fontFamily = FontFamily(Font(R.font.cookie_run_regular))),
            titleSmall = Typography.titleSmall.copy(fontFamily = FontFamily(Font(R.font.cookie_run_regular))),
            bodyLarge = Typography.bodyLarge.copy(fontFamily = FontFamily(Font(R.font.cookie_run_regular))),
            bodyMedium = Typography.bodyMedium.copy(fontFamily = FontFamily(Font(R.font.cookie_run_regular))),
            bodySmall = Typography.bodySmall.copy(fontFamily = FontFamily(Font(R.font.cookie_run_regular))),
            labelLarge = Typography.labelLarge.copy(fontFamily = FontFamily(Font(R.font.cookie_run_regular))),
            labelMedium = Typography.labelMedium.copy(fontFamily = FontFamily(Font(R.font.cookie_run_regular))),
            labelSmall = Typography.labelSmall.copy(fontFamily = FontFamily(Font(R.font.cookie_run_regular))),
        ),
        content = content
    )
}