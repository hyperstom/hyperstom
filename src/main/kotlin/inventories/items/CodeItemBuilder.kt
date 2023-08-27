package emeraldwater.infernity.dev.inventories.items

import emeraldwater.infernity.dev.interpreter.Action
import emeraldwater.infernity.dev.interpreter.EnumAction
import emeraldwater.infernity.dev.interpreter.PlayerAction
import emeraldwater.infernity.dev.items.DevItems
import emeraldwater.infernity.dev.mm
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag

enum class Color(val miniColor: String) {
    TEXT("<aqua>"),
    RICH_TEXT("<#55aa00>"),
    NUMBER("<red>"),
    VARIABLE("<yellow>"),
    LIST("<dark_green>"),
    DICTIONARY("<#aaaaff>"),
    ANY_VALUE("<#ffd4aa>"),
    BLOCK("<gold>"),
}

enum class Type(val mini: String) {
    TEXT("<aqua>Text"),
    RICH_TEXT("<#55aa00>Rich Text"),
    NUMBER("<red>Number"),
    VARIABLE("<yellow>Variable"),
    LIST("<dark_green>List"),
    DICTIONARY("<#aaaaff>Dictionary"),
    ANY_VALUE("<#ffd4aa>Game Value"),
    BLOCK("<gold>Block"),
}

class DevItemBuilder {
    var description: List<String> = listOf()
    var name: String = ""
    var material: Material = Material.STONE

    fun name(name: String): DevItemBuilder {
        this.name = name
        return this
    }
    fun item(item: Material): DevItemBuilder {
        this.material = item
        return this
    }
    fun description(desc: String): DevItemBuilder {
        description += desc.split('\n')
        return this
    }
    fun parameter(type: Type, use: String): DevItemBuilder {
        if(!description.contains("<white>Chest Parameters:")) {
            description += listOf("", "<white>Chest Parameters:")
        }
        description += "${type.mini} <dark_gray>- <gray>$use"
        return this
    }
    fun parameterPlural(type: Type, use: String): DevItemBuilder {
        if(!description.contains("<white>Chest Parameters:")) {
            description += listOf("", "<white>Chest Parameters:")
        }
        description += "${type.mini}(s) <dark_gray>- <gray>$use"
        return this
    }
    fun build(action: EnumAction): ItemStack {
        return ItemStack.builder(material)
            .displayName(mm(name))
            .lore(description.map { mm("<gray>$it") })
            .build()
            .withTag(Tag.String("changeSign"), action.signText)
    }
}