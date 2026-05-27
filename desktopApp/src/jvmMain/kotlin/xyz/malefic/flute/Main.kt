package xyz.malefic.flute

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry

fun main() {
    val lifecycle = LifecycleRegistry()

    application {
        val root =
            remember {
                DefaultRootComponent(
                    componentContext = DefaultComponentContext(lifecycle = lifecycle),
                )
            }

        val windowState = rememberWindowState()
        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Flute",
        ) {
            App(root)
        }
    }
}
