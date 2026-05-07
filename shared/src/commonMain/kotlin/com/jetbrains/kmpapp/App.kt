package com.jetbrains.kmpapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.jetbrains.kmpapp.screens.DemoScreen
import com.jetbrains.kmpapp.screens.EmptyScreenContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    component: RootComponent,
    colorScheme: ColorScheme? = null,
) {
    val childStack by component.stack.subscribeAsState()
    val activeChild = childStack.active.instance

    val appColorScheme = colorScheme ?: if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()

    MaterialTheme(
        colorScheme = appColorScheme,
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            TopAppBar(
                title = {
                    Text(
                        when (activeChild) {
                            is RootComponent.Child.HomeChild -> "Home"
                            is RootComponent.Child.DemoChild -> "Demo"
                        },
                    )
                },
                actions = {
                    TextButton(onClick = component::onHomeTabClick) { Text("Home") }
                    TextButton(onClick = component::onDemoTabClick) { Text("Demo") }
                },
            )
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when (activeChild) {
                    is RootComponent.Child.HomeChild -> EmptyScreenContent(Modifier.fillMaxWidth())
                    is RootComponent.Child.DemoChild -> DemoScreen(Modifier.fillMaxWidth())
                }
            }
        }
    }
}
