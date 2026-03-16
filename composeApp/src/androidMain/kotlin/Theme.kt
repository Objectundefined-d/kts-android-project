import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightColors = lightColorScheme(
    primary = Color(0xFF00A86B),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB7F0D8),
    onPrimaryContainer = Color(0xFF00321F),
    secondary = Color(0xFF4CAF50),
    onSecondary = Color.White,
    background = Color(0xFFF5F7FA),
    onBackground = Color(0xFF1A1A1A),
    surface = Color.White,
    onSurface = Color(0xFF1A1A1A),
    surfaceVariant = Color(0xFFEEF0F3),
    onSurfaceVariant = Color(0xFF555555),
    error = Color(0xFFD32F2F),
    onError = Color.White,
    outline = Color(0xFFCCCCCC)
)

val DarkColors = darkColorScheme(
    primary = Color(0xFF00C47C),
    onPrimary = Color(0xFF00321F),
    primaryContainer = Color(0xFF00513A),
    onPrimaryContainer = Color(0xFFB7F0D8),
    secondary = Color(0xFF66BB6A),
    onSecondary = Color(0xFF003300),
    background = Color(0xFF111314),
    onBackground = Color(0xFFE2E2E2),
    surface = Color(0xFF1C1F20),         
    onSurface = Color(0xFFE2E2E2),
    surfaceVariant = Color(0xFF252A2B),
    onSurfaceVariant = Color(0xFFAAAAAA),
    error = Color(0xFFEF5350),
    onError = Color(0xFF601410),
    outline = Color(0xFF444444)
)

@Composable
fun KtsProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}