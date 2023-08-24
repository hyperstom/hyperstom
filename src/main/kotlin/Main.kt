package emeraldwater.infernity.dev

import emeraldwater.infernity.dev.commands.handleCommand
import emeraldwater.infernity.dev.events.onBreakBlock
import emeraldwater.infernity.dev.events.onPlaceBlock
import emeraldwater.infernity.dev.plots.Plot
import emeraldwater.infernity.dev.plots.PlotMode
import emeraldwater.infernity.dev.plots.PlotState
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.Player
import net.minestom.server.entity.metadata.display.TextDisplayMeta
import net.minestom.server.event.GlobalEventHandler
import net.minestom.server.event.player.PlayerBlockBreakEvent
import net.minestom.server.event.player.PlayerBlockInteractEvent
import net.minestom.server.event.player.PlayerBlockPlaceEvent
import net.minestom.server.event.player.PlayerCommandEvent
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.event.trait.PlayerInstanceEvent
import net.minestom.server.extras.MojangAuth
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.InstanceManager
import net.minestom.server.instance.block.Block
import net.minestom.server.world.DimensionType

lateinit var instanceHub: InstanceContainer
var playerModes = mutableMapOf<String, PlotState>()

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val server: MinecraftServer = MinecraftServer.init()
        MojangAuth.init()
        server.start("0.0.0.0", 25565)
        val instanceManager = MinecraftServer.getInstanceManager()
        // Create the instance
        val instanceContainer = instanceManager.createInstanceContainer(DimensionType.OVERWORLD)
        instanceHub = instanceContainer
        // Set the ChunkGenerator
        instanceHub.setGenerator { unit ->
            unit.modifier().fillHeight(49, 50, Block.GRASS_BLOCK)
        }

        // Create the text display at spawn
        val display = Entity(EntityType.TEXT_DISPLAY)
        val future = display.setInstance(instanceHub, Pos(2.0, 55.0, 0.0))
        future.thenRun {
            display.teleport(Pos(2.0, 57.0, 0.0))
        }
        val meta = display.entityMeta as TextDisplayMeta
        meta.text = MiniMessage.miniMessage().deserialize(
            """
<gray>Welcome to <green>Emerald<blue>Water<gray>.
<gray>Developed by <red>Infernity <gray>and <light_purple>Endistic<gray>.
""".trimIndent())

        // Add an event callback to specify the spawning instance (and the spawn position)
        val globalEventHandler: GlobalEventHandler = MinecraftServer.getGlobalEventHandler()
        globalEventHandler.addListener(PlayerLoginEvent::class.java) { event ->
            val player: Player = event.player
            event.setSpawningInstance(instanceHub)
            player.respawnPoint = Pos(0.0, 52.0, 0.0)
            playerModes[player.username] = PlotState(0, PlotMode.IN_HUB)
        }

        globalEventHandler.addListener(PlayerCommandEvent::class.java) { event ->
            val player = event.player
            val command = event.command
            handleCommand(command, player)
        }
        globalEventHandler.addListener(PlayerBlockBreakEvent::class.java) { onBreakBlock(it) }
        globalEventHandler.addListener(PlayerBlockPlaceEvent::class.java) { onPlaceBlock(it) }

    }
}

