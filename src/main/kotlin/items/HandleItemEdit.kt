package emeraldwater.infernity.dev.items

import emeraldwater.infernity.dev.mm
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.minestom.server.event.player.PlayerChatEvent
import net.minestom.server.tag.Tag

fun handleEditItem(event: PlayerChatEvent) {
    val item = event.player.itemInMainHand
    val player = event.player
    if(item.getTag(Tag.String("varitem.id")) == "txt") {
        event.isCancelled = true
        player.itemInMainHand = item
            .withDisplayName(Component.text(event.message))
            .withLore(listOf())
            .withTag(Tag.String("varitem.value"), event.message)
    }
    if(item.getTag(Tag.String("varitem.id")) == "rtxt") {
        event.isCancelled = true
        player.itemInMainHand = item
            .withDisplayName(mm(event.message))
            .withLore(listOf(
                mm("<gray>Expression:"),
                Component.text(event.message, Style.style(TextColor.color(255, 255, 255)))
            ))
            .withTag(Tag.Component("varitem.value"), mm(event.message))
    }
    if(item.getTag(Tag.String("varitem.id")) == "num") {
        event.isCancelled = true
        try {
            player.itemInMainHand = item
                .withDisplayName(mm("<red>" + event.message))
                .withLore(listOf())
                .withTag(Tag.Double("varitem.value"), event.message.toDouble())
        } catch(e: NumberFormatException) {
            player.sendMessage(mm("<red>${event.message} is not a valid number."))
        }

    }
}