package xyz.malefic.flute

import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import compose.icons.Octicons
import compose.icons.octicons.Beaker24
import compose.icons.octicons.Home24
import kotlinx.serialization.Serializable

interface RootComponent {
    val stack: Value<ChildStack<*, Screen>>

    val topLevelScreens: List<Screen>

    fun navigateTo(screen: Screen)

    @Serializable
    enum class Screen(val title: String, val icon: ImageVector) {
        Home("Home", Octicons.Home24),
        Demo("Demo", Octicons.Beaker24),
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent,
    ComponentContext by componentContext {
    private val navigation = StackNavigation<RootComponent.Screen>()

    override val stack: Value<ChildStack<*, RootComponent.Screen>> =
        childStack(
            source = navigation,
            serializer = RootComponent.Screen.serializer(),
            initialConfiguration = RootComponent.Screen.Home,
            handleBackButton = true,
            childFactory = ::screenFactory,
        )

    override val topLevelScreens: List<RootComponent.Screen> =
        listOf(
            RootComponent.Screen.Home,
            RootComponent.Screen.Demo,
        )

    private fun screenFactory(
        screen: RootComponent.Screen,
        @Suppress("UNUSED_PARAMETER") componentContext: ComponentContext,
    ): RootComponent.Screen = screen

    override fun navigateTo(screen: RootComponent.Screen) = navigation.bringToFront(screen)
}
