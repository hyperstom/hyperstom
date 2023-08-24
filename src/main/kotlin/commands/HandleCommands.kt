package emeraldwater.infernity.dev.commands

import emeraldwater.infernity.dev.instanceHub
import emeraldwater.infernity.dev.playerModes
import emeraldwater.infernity.dev.plots.Plot
import emeraldwater.infernity.dev.plots.PlotMode
import emeraldwater.infernity.dev.plots.PlotState
import emeraldwater.infernity.dev.plots.plots
import net.kyori.adventure.text.minimessage.MiniMessage
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.GameMode
import net.minestom.server.entity.Player

/**
 * Handles logic for /join and /play when not in a plot. This assumes that arguments has at least 2 elements, the command name and the plot id to join.
 * @param player the player who is joining.
 * @param arguments the command arguments.
 */
fun handleJoinCommandLogic(player: Player, arguments: List<String>) {
    val id = arguments[1].toInt()
    val filtered = plots.filter { it.id == id }
    if(filtered.size == 1) {
        val plot = filtered[0]
        plot.joinInstance(player)
    } else {
        val plot = Plot(id)
        plot.joinInstance(player)
    }
}

fun handleCommand(command: String, player: Player) {
    val arguments = command.split(' ')
    when(arguments[0]) {
        "join" -> {
            if (arguments.size == 1) {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You must provide a plot id!"))
                return
            }
            handleJoinCommandLogic(player, arguments)
        }
        "play" -> {
            val mode = playerModes[player.username]!!
            if(mode.mode != PlotMode.IN_HUB) {
                playerModes[player.username] = PlotState(mode.id, PlotMode.PLAY)
                player.teleport(Pos(1.0, 52.0, 1.0))
                player.setGameMode(GameMode.SURVIVAL)
                player.inventory.clear()
            } else {
                if (arguments.size == 1) {
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You must provide a plot id, or be on a plot to enter play mode!"))
                    return
                }
                handleJoinCommandLogic(player, arguments)
            }
        }
        "build" -> {
            val mode = playerModes[player.username]!!
            if(mode.mode != PlotMode.IN_HUB) {
                playerModes[player.username] = PlotState(mode.id, PlotMode.BUILD)
                player.teleport(Pos(1.0, 52.0, 1.0))
                player.setGameMode(GameMode.CREATIVE)
                player.inventory.clear()
            } else {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You must be on a plot to use this command!"))
            }
        }
        "dev", "code" -> {
            val mode = playerModes[player.username]!!
            if(mode.mode != PlotMode.IN_HUB) {
                playerModes[player.username] = PlotState(mode.id, PlotMode.DEV)
                player.teleport(Pos(-1019.5, 3.0, 0.5))
                player.setGameMode(GameMode.CREATIVE)
                player.inventory.clear()
            } else {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You must be on a plot to use this command!"))
            }
        }
        "leave" -> {
            if (playerModes[player.username]?.mode == PlotMode.IN_HUB) {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You must be in a plot to leave it!"))
                return
            }
            player.sendMessage(MiniMessage.miniMessage().deserialize("Leaving plot id ${playerModes[player.username]!!.id}..."))
            val future = player.setInstance(instanceHub)
            playerModes[player.username] = PlotState(0, PlotMode.IN_HUB)
            future.thenRun {
                player.teleport(Pos(0.0, 52.0, 0.0))
            }
        }
        else -> {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Invalid command! Type /help for help."))
        }
    }
}