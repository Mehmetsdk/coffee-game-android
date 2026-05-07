package com.example.minicafegame.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CoffeeColorScheme = lightColorScheme(
    primary = CoffeeBrown,
    onPrimary = CreamWhite,
    primaryContainer = CoffeeMedium,
    onPrimaryContainer = CreamWhite,
    secondary = WarmAmber,
    onSecondary = CoffeeDark,
    secondaryContainer = GoldenYellow,
    onSecondaryContainer = CoffeeDark,
    background = CreamLight,
    onBackground = CoffeeDark,
    surface = CreamWhite,
    onSurface = CoffeeBrown,
    surfaceVariant = Color(0xFFEFE9E3),
    outline = CoffeeLight
)

@Composable
fun MiniCafeGameTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CoffeeColorScheme,
        typography = Typography,
        content = content
    )
}
