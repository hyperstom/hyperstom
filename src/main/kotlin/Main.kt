package emeraldwater.infernity.dev

import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Player
import net.minestom.server.event.GlobalEventHandler
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.extras.MojangAuth
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.InstanceManager
import net.minestom.server.instance.block.Block

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val server: MinecraftServer = MinecraftServer.init()
        MojangAuth.init()
        server.start("0.0.0.0", 25565)
        val instanceManager: InstanceManager = MinecraftServer.getInstanceManager()
        // Create the instance
        val instanceContainer: InstanceContainer = instanceManager.createInstanceContainer()
        // Set the ChunkGenerator
        instanceContainer.setGenerator { unit ->
            unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK)
        }
        // Add an event callback to specify the spawning instance (and the spawn position)
        val globalEventHandler: GlobalEventHandler = MinecraftServer.getGlobalEventHandler()
        globalEventHandler.addListener(PlayerLoginEvent::class.java) { event ->
            val player: Player = event.getPlayer()
            event.setSpawningInstance(instanceContainer)
            player.setRespawnPoint(Pos(0.0, 42.0, 0.0))
        }
    }
}