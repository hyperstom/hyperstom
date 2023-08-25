package emeraldwater.infernity.dev.commands

import emeraldwater.infernity.dev.instanceHub
import emeraldwater.infernity.dev.playerModes
import emeraldwater.infernity.dev.plots.*
import net.kyori.adventure.text.minimessage.MiniMessage
import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.GameMode
import net.minestom.server.entity.Player

/**
 * Handles logic for "joining plot 0" or the /leave command.
 */
fun handleLeavingLogic(player: Player){
    if (playerModes[player.username]?.mode == PlotMode.IN_HUB) {
        player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You must be in a plot to leave it!"))
        return
    }
    player.sendMessage(MiniMessage.miniMessage().deserialize("Leaving plot id ${playerModes[player.username]!!.id}..."))
    val future = player.setInstance(instanceHub)
    playerModes[player.username] = PlotState(0, PlotMode.IN_HUB)
    player.gameMode = GameMode.SURVIVAL
    future.thenRun {
        player.teleport(Pos(0.0, 52.0, 0.0))
    }
}

/**
 * Handles logic for /join and /play when not in a plot. This assumes that arguments has at least 2 elements, the command name and the plot id to join.
 * @param player the player who is joining.
 * @param id the plot id to join.
 */
fun handleJoinCommandLogic(player: Player, id: Int) {
    val filtered = plots.filter { it.id == id }
    if(id == 0){
        handleLeavingLogic(player)
        return
    }
    if(filtered.size == 1) {
        val plot = filtered[0]
        plot.joinInstance(player)
    } else {
        val plot = Plot(id)
        plot.joinInstance(player)
    }
}

object JoinCommand : Command("join") {
    init {
        setDefaultExecutor { sender, context -> sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>You must provide a plot id!")) }

        val plotId = ArgumentType.Integer("plot id")

        addSyntax({ sender, context ->
            val plotIdNum: Int = context.get(plotId)
            handleJoinCommandLogic(sender as Player, plotIdNum)
        }, plotId)
    }
}

object PlayCommand : Command("play") {
    init {
        setDefaultExecutor { sender, context ->
            val player = sender as Player
            val mode = playerModes[player.username]!!
            if (mode.mode != PlotMode.IN_HUB) {
            playerModes[player.username] = PlotState(mode.id, PlotMode.PLAY)
            try { player.setInstance(filterPlot(mode.id).buildInstance) } catch(_: Exception) {}
            player.teleport(Pos(1.0, 52.0, 1.0))
            player.setGameMode(GameMode.SURVIVAL)
            player.inventory.clear()
            } else {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You must provide a plot id, or be on a plot to enter play mode!"))
            }
        }

        val plotId = ArgumentType.Integer("plot id")

        addSyntax({ sender, context ->
            val plotIdNum: Int = context.get(plotId)
            handleJoinCommandLogic(sender as Player, plotIdNum)
        }, plotId)
    }
}

object LeaveCommand : Command("leave") {
    init {
        setDefaultExecutor { sender, _ -> handleLeavingLogic(sender as Player)}
    }
}

fun handleCommandRegistration(){
    MinecraftServer.getCommandManager().register(JoinCommand)
    MinecraftServer.getCommandManager().register(PlayCommand)
    MinecraftServer.getCommandManager().register(LeaveCommand)
}

fun handleCommand(command: String, player: Player) {
    return /* bypass code here since its being refactored
    val arguments = command.split(' ')
    when(arguments[0]) {
        "build" -> {
            val mode = playerModes[player.username]!!
            if(mode.mode != PlotMode.IN_HUB) {
                playerModes[player.username] = PlotState(mode.id, PlotMode.BUILD)
                try { player.setInstance(filterPlot(mode.id).buildInstance) } catch(_: Exception) {}
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
                filterPlot(mode.id).joinDev(player)
            } else {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You must be on a plot to use this command!"))
            }
        }
        "saveplots" -> {
            player.sendMessage("Saving plots to storage...")
            for(plot in plots) {
                val container = plot.buildInstance
                val saved = container.saveChunksToStorage()
                saved.thenRun {
                    player.sendMessage("Saved plot #${plot.id} to storage.")
                }
            }
            player.sendMessage("Casted all futures.")
        }
        "state" -> player.sendMessage("${playerModes[player.username]!!}")
        else -> {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Invalid command! Type /help for help."))
        }
    }*/
}