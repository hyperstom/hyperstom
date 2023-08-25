package emeraldwater.infernity.dev.events

import dispatchInventory
import emeraldwater.infernity.dev.playerTargets
import net.minestom.server.event.player.PlayerBlockInteractEvent
import net.minestom.server.tag.Tag

fun detectRightClick(event: PlayerBlockInteractEvent) {
    val player = event.player
    val block = event.block

    if(block.getTag(Tag.String("codeBlockType")) == "sign") {
        playerTargets[player.username] = event.blockPosition
        dispatchInventory(player)
    }
}