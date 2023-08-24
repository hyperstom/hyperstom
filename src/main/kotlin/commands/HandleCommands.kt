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

fun handleCommand(command: String, player: Player) {
    val arguments = command.split(' ')
    when(arguments[0]) {
        "join" -> {
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
        "play" -> {
            val mode = playerModes[player.username]!!
            if(mode.mode != PlotMode.IN_HUB) {
                playerModes[player.username] = PlotState(mode.id, PlotMode.PLAY)
                player.teleport(Pos(1.0, 52.0, 1.0))
                player.setGameMode(GameMode.SURVIVAL)
                player.inventory.clear()
            } else {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You must be on a plot to use this command!"))
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
            val future = player.setInstance(instanceHub)
            future.thenRun {
                player.teleport(Pos(0.0, 52.0, 0.0))
            }
        }
        "saveplots" -> {
            player.sendMessage("Saving plots to storage...")
            for(plot in plots) {
                val container = plot.instanceContainer
                val saved = container.saveChunksToStorage()
                saved.thenRun {
                    player.sendMessage("Saved plot #${plot.id} to storage.")
                }
            }
            player.sendMessage("Casted all futures.")
        }
        else -> {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Invalid command! Type /help for help."))
        }
    }
}