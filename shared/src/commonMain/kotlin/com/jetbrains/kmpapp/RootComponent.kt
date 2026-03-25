package com.jetbrains.kmpapp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    fun onHomeTabClick()

    fun onDemoTabClick()

    sealed class Child {
        class HomeChild : Child()

        class DemoChild : Child()
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent,
    ComponentContext by componentContext {
    @Serializable
    sealed class Config {
        @Serializable
        data object Home : Config()

        @Serializable
        data object Demo : Config()
    }

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Home,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(
        config: Config,
        @Suppress("UNUSED_PARAMETER") componentContext: ComponentContext,
    ): RootComponent.Child =
        when (config) {
            is Config.Home -> RootComponent.Child.HomeChild()
            is Config.Demo -> RootComponent.Child.DemoChild()
        }

    override fun onHomeTabClick() = navigation.bringToFront(Config.Home)

    override fun onDemoTabClick() = navigation.bringToFront(Config.Demo)
}
