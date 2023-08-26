package emeraldwater.infernity.dev.items

import emeraldwater.infernity.dev.mm
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag
import org.jglrxavpok.hephaistos.nbt.NBT

object DevItems {
    /*
    Code Blocks
     */
    val playerAction = ItemStack.builder(Material.COBBLESTONE)
        .displayName(mm("<green>Player Action"))
        .lore(
            mm("<gray>Represents an action done to a"),
            mm("<gray>player."),
            mm(""),
            mm("<light_purple>Examples:"),
            mm("<dark_gray>- <gray>Send a message to a player"),
            mm("<dark_gray>- <gray>Give a player an item"),
            mm("<dark_gray>- <gray>Display a particle to a player"),
        )
        .build()

    val playerEvent = ItemStack.builder(Material.DIAMOND_BLOCK)
        .displayName(mm("<green>Player Event"))
        .lore(
            mm("<gray>Runs this code when a player"),
            mm("<gray>does an event."),
            mm(""),
            mm("<light_purple>Examples:"),
            mm("<dark_gray>- <gray>When a player left clicks"),
            mm("<dark_gray>- <gray>When a player takes damage"),
            mm("<dark_gray>- <gray>When a player sends a chat message"),
        )
        .build()

    val setVariable = ItemStack.builder(Material.IRON_BLOCK)
        .displayName(mm("<yellow>Set Variable"))
        .lore(
            mm("<gray>Represents an action done to a"),
            mm("<gray>variable."),
            mm(""),
            mm("<light_purple>Examples:"),
            mm("<dark_gray>- <gray>Set a variable to a value"),
            mm("<dark_gray>- <gray>Add two numbers together"),
            mm("<dark_gray>- <gray>Manipulate text values"),
        )
        .build()
    /*
    Value Items
     */
    val text = ItemStack.builder(Material.BOOK)
        .displayName(mm("<aqua>Text"))
        .lore(
            mm("<gray>Represents a text value."),
            mm(""),
            mm("<light_purple>How to change value:"),
            mm("<gray>Type the new value in chat.")
        )
        .build()
        .withTag(Tag.String("varitem.id"), "txt")
        .withTag(Tag.String("varitem.value"), "")

    val richText = ItemStack.builder(Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE)
        .meta(NBT.Compound(mapOf(
            "HideFlags" to NBT.Int(255)
        )))
        .displayName(mm("<#55aa00>Rich Text"))
        .lore(
            mm("<gray>Represents a rich text value."),
            mm(""),
            mm("<light_purple>How to change value:"),
            mm("<gray>Type the new value in chat.")
        )
        .build()
        .withTag(Tag.String("varitem.id"), "rtxt")
        .withTag(Tag.String("varitem.value"), "")


    val number = ItemStack.builder(Material.SLIME_BALL)
        .displayName(mm("<red>Number"))
        .lore(
            mm("<gray>Represents a number value."),
            mm(""),
            mm("<light_purple>How to change value:"),
            mm("<gray>Type the new value in chat.")
        )
        .build()
        .withTag(Tag.String("varitem.id"), "num")
        .withTag(Tag.Double("varitem.value"), 0.0)

    val valuesItem = ItemStack.builder(Material.IRON_INGOT)
        .displayName(mm("<white>Values"))
        .lore(
            mm("<gray>Right-click to open a menu with"),
            mm("<gray>all available values.")
        )
        .build()

    val codeBlocksItem = ItemStack.builder(Material.DIAMOND)
        .displayName(mm("<aqua>Code Blocks"))
        .lore(
            mm("<gray>Right-click to open a menu with"),
            mm("<gray>all available code blocks.")
        )
        .build()
}