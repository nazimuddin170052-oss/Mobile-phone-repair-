package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ZukiDarkColorScheme = darkColorScheme(
  primary = TurquoiseBright,
  onPrimary = Navy900,
  primaryContainer = Navy700,
  onPrimaryContainer = TurquoiseBright,
  secondary = TurquoisePrimary,
  onSecondary = Navy900,
  secondaryContainer = Navy600,
  onSecondaryContainer = TurquoiseContainer,
  tertiary = AmberAlert,
  onTertiary = Navy900,
  background = Navy900,
  onBackground = CleanWhite,
  surface = Navy800,
  onSurface = CleanWhite,
  surfaceVariant = Navy700,
  onSurfaceVariant = Slate300,
  error = CrimsonDanger,
  onError = CleanWhite
)

private val ZukiLightColorScheme = lightColorScheme(
  primary = Navy800,
  onPrimary = CleanWhite,
  primaryContainer = TurquoiseContainer,
  onPrimaryContainer = Navy800,
  secondary = TurquoisePrimary,
  onSecondary = CleanWhite,
  secondaryContainer = Slate100,
  onSecondaryContainer = Navy800,
  tertiary = AmberAlert,
  onTertiary = Navy900,
  background = OffWhite,
  onBackground = Navy900,
  surface = CleanWhite,
  onSurface = Navy900,
  surfaceVariant = Slate100,
  onSurfaceVariant = Slate600,
  error = CrimsonDanger,
  onError = CleanWhite
)

@Composable
fun ZukiServicingTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) ZukiDarkColorScheme else ZukiLightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
