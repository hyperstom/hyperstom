package emeraldwater.infernity.dev

import emeraldwater.infernity.dev.commands.handleCommandRegistration
import emeraldwater.infernity.dev.events.*
import emeraldwater.infernity.dev.interpreter.Interpreter
import emeraldwater.infernity.dev.interpreter.PlayerEvent
import emeraldwater.infernity.dev.interpreter.interpret
import emeraldwater.infernity.dev.inventories.clickVarItem
import emeraldwater.infernity.dev.inventories.handleBarrelClose
import emeraldwater.infernity.dev.inventories.items.onClickItem
import emeraldwater.infernity.dev.items.checkValuesMenu
import emeraldwater.infernity.dev.items.handleEditItem
import emeraldwater.infernity.dev.plots.PlotMode
import emeraldwater.infernity.dev.plots.PlotState
import net.hollowcube.block.placement.HCPlacementRules
import net.kyori.adventure.text.minimessage.MiniMessage
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.Player
import net.minestom.server.entity.metadata.display.TextDisplayMeta
import net.minestom.server.event.GlobalEventHandler
import net.minestom.server.event.inventory.InventoryClickEvent
import net.minestom.server.event.inventory.InventoryCloseEvent
import net.minestom.server.event.inventory.InventoryPreClickEvent
import net.minestom.server.event.player.*
import net.minestom.server.extras.MojangAuth
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.block.Block
import net.minestom.server.world.DimensionType

lateinit var instanceHub: InstanceContainer
var playerModes = mutableMapOf<String, PlotState>()
var playerTargets = mutableMapOf<String, Point>()
var playerInterpreter = mutableMapOf<String, Interpreter>()
val players: List<Player> = mutableListOf()
fun mm(string: String) = MiniMessage.miniMessage().deserialize("<!italic>$string")

val barrelName = mm("Block Arguments")
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

        // Register commands
        handleCommandRegistration()
        // Make placing stairs and stuff like that work
        // HCPlacementRules.init()

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
        globalEventHandler.addListener(PlayerBlockInteractEvent::class.java) { event ->
            detectRightClick(event)
        }
        globalEventHandler.addListener(PlayerBlockBreakEvent::class.java) { onBreakBlock(it) }
        globalEventHandler.addListener(PlayerBlockPlaceEvent::class.java) { onPlaceBlock(it) }
        globalEventHandler.addListener(InventoryPreClickEvent::class.java) { clickVarItem(it) }
        globalEventHandler.addListener(InventoryClickEvent::class.java) { onClickItem(it) }
        globalEventHandler.addListener(PlayerChatEvent::class.java) { overrideChat(it); handleEditItem(it); }
        globalEventHandler.addListener(InventoryCloseEvent::class.java) { handleBarrelClose(it) }
        globalEventHandler.addListener(PlayerUseItemEvent::class.java) { checkValuesMenu(it) }

        globalEventHandler.addListener(PlayerTickEvent::class.java) {
            it.player.interpret(PlayerEvent.TICK)
        }
        globalEventHandler.addListener(PlayerStartSneakingEvent::class.java) {
            it.player.interpret(PlayerEvent.SNEAK)
        }
        globalEventHandler.addListener(PlayerSwapItemEvent::class.java) {
            it.player.interpret(PlayerEvent.SWAP_HANDS)
        }
    }
}

