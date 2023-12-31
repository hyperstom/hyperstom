package emeraldwater.infernity.dev.events

import emeraldwater.infernity.dev.mm
import net.minestom.server.event.player.PlayerChatEvent

fun overrideChat(event: PlayerChatEvent) {
    event.setChatFormat {
        if (it.player.username == "Endistic" || it.player.username == "Infyrnity") {
            return@setChatFormat mm("<dark_purple>[DEV] ${event.player.username}<white>: ${event.message}")
        }
        return@setChatFormat mm("<gray>${event.player.username}<gray>: ${event.message}")
    }
}