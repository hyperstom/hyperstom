package emeraldwater.infernity.dev.events

import emeraldwater.infernity.dev.mm
import emeraldwater.infernity.dev.playerModes
import net.minestom.server.event.player.PlayerChatEvent

fun overrideChat(event: PlayerChatEvent) {
    event.setChatFormat {
        if(it.player.username == "Endistic" || it.player.username == "Infernity") {
            return@setChatFormat mm("<dark_purple>[DEV] ${event.player.username}<white>: ${event.message}")
        }
        return@setChatFormat mm("<gray>${event.player.username}<gray>: ${event.message}")
    }
}