package com.jetbrains.kmpapp

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle

@Suppress("ktlint:standard:function-naming", "unused", "FunctionName")
fun MainViewController() =
    ComposeUIViewController {
        val root =
            remember {
                DefaultRootComponent(
                    componentContext = DefaultComponentContext(lifecycle = ApplicationLifecycle()),
                )
            }
        App(root)
    }
