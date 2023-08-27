package emeraldwater.infernity.dev.interpreter

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.minestom.server.item.ItemStack

sealed class Argument {
    class Text(val value: String) : Argument()
    class RichText(val value: Component) : Argument()
    class Number(val value: Double) : Argument()
    class Item(val value: ItemStack) : Argument()
    class FunctionReference(val value: String) : Argument()
    override fun toString(): String {
        return when(this) {
            is Text -> "\"$value\""
            is RichText -> "$\"${MiniMessage.miniMessage().serialize(value)}<reset>\""
            is Number -> "$value"
            is Item -> "${value.displayName}"
            is FunctionReference -> "&\"$value\""
        }
    }
}