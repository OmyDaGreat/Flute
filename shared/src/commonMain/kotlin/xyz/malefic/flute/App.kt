package xyz.malefic.flute

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import compose.icons.Octicons
import compose.icons.octicons.Beaker24
import compose.icons.octicons.Home24
import xyz.malefic.flute.screens.DemoScreen
import xyz.malefic.flute.screens.EmptyScreenContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    component: RootComponent,
    colorScheme: ColorScheme? = null,
) {
    val childStack by component.stack.subscribeAsState()
    val activeScreen = childStack.active.instance

    val appColorScheme = colorScheme ?: if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()

    MaterialTheme(
        colorScheme = appColorScheme,
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            TopAppBar(
                title = {
                    Text(activeScreen.title)
                },
                actions = {
                    component.topLevelScreens.forEach { screen ->
                        TextButton(onClick = { component.navigateTo(screen) }) {
                            Icon(screen.icon, screen.title)
                        }
                    }
                },
            )
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when (activeScreen) {
                    RootComponent.Screen.Home -> EmptyScreenContent(Modifier.fillMaxWidth())
                    RootComponent.Screen.Demo -> DemoScreen(Modifier.fillMaxWidth())
                }
            }
        }
    }
}

private val RootComponent.Screen.icon: ImageVector
    get() =
        when (this) {
            RootComponent.Screen.Home -> Octicons.Home24
            RootComponent.Screen.Demo -> Octicons.Beaker24
        }
