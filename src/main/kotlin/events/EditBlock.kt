package emeraldwater.infernity.dev.events

import emeraldwater.infernity.dev.playerModes
import emeraldwater.infernity.dev.plots.PlotMode
import net.minestom.server.event.player.PlayerBlockBreakEvent
import net.minestom.server.event.player.PlayerBlockPlaceEvent

/**
 * Handles the logic for placing blocks.
 * @param event The event to get the information from.
 */
fun onPlaceBlock(event: PlayerBlockPlaceEvent) {
    event.isCancelled = true
    val player = event.player
    if (playerModes[player.username]!!.mode == PlotMode.BUILD) {
        event.isCancelled = false
    }
    if (playerModes[player.username]!!.mode == PlotMode.DEV) {
        event.isCancelled = false
        placeDevBlock(event)
    }
}

/**
 * Handles the logic for breaking blocks.
 * @param event The event to get the information from.
 */
fun onBreakBlock(event: PlayerBlockBreakEvent) {
    event.isCancelled = true
    val player = event.player
    if (playerModes[player.username]!!.mode == PlotMode.BUILD) {
        event.isCancelled = false
    }
    if (playerModes[player.username]!!.mode == PlotMode.DEV) {
        breakDevBlock(event)
    }
}

