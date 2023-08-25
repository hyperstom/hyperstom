package emeraldwater.infernity.dev.plots

import emeraldwater.infernity.dev.playerModes
import net.hollowcube.polar.PolarLoader
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.GameMode
import net.minestom.server.entity.Player
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.block.Block
import net.minestom.server.world.DimensionType
import java.io.File
import java.lang.Exception
import java.util.concurrent.CompletableFuture
import kotlin.io.path.Path

var plots = mutableListOf<Plot>()

data class Plot(val id: Int) {
    val buildInstance: InstanceContainer
    val devInstance: InstanceContainer
    init {
        val instanceManager = MinecraftServer.getInstanceManager()
        buildInstance = instanceManager.createInstanceContainer(DimensionType.OVERWORLD)
        devInstance = instanceManager.createInstanceContainer(DimensionType.OVERWORLD)
        val buildWorldFile = File("./worlds/build-$id.polar")
        if(!buildWorldFile.exists()) {
            buildInstance.chunkLoader = PolarLoader(Path("./worlds/build-$id.polar"))
            for(x in 0..128) {
                for(z in 0..128) {
                    buildInstance.setBlock(x, 50, z, Block.GRASS_BLOCK)
                    if(x == 0 || z == 0 || x == 128 || z == 128) {
                        buildInstance.setBlock(x, 50, z, Block.WHITE_WOOL)
                    }
                }
            }
        }
        val devWorldFile = File("./worlds/dev-$id.polar")
        if(!devWorldFile.exists()) {
            devInstance.chunkLoader = PolarLoader(Path("./worlds/dev-$id.polar"))
            for(x in -0 downTo -20 step 3) {
                for(z in 0..128) {
                    for(y in 1..255 step 10) {
                        devInstance.setBlock(x, y, z, Block.WHITE_STAINED_GLASS)
                    }
                }
            }
        }
        buildInstance.chunkLoader = PolarLoader(Path("./worlds/build-$id.polar"))
        devInstance.chunkLoader = PolarLoader(Path("./worlds/dev-$id.polar"))
        buildInstance.saveChunksToStorage()
        buildInstance.saveInstance()
        devInstance.saveChunksToStorage()
        devInstance.saveInstance()
        plots.add(this)

    }

    fun joinInstance(player: Player) {
        player.sendMessage("Taking you to plot ID #$id...")
        val future = player.setInstance(buildInstance)
        playerModes[player.username] = PlotState(id, PlotMode.PLAY)
        future.thenRun {
            player.sendMessage("You have joined plot ID #$id")
            player.teleport(Pos(0.5, 52.0, 0.5))
            player.setGameMode(GameMode.CREATIVE)
        }
    }

    fun joinDev(player: Player) {
        player.sendMessage("Taking you to the devspace of plot ID #$id...")
        val mode = playerModes[player.username]!!
        val future: CompletableFuture<Void> = try { player.setInstance(filterPlot(mode.id).devInstance) } catch(e: Exception) { return }
        playerModes[player.username] = PlotState(id, PlotMode.DEV)
        future.thenRun {
            player.sendMessage("You have joined plot ID #$id")
            player.teleport(Pos(0.5, 4.0, 0.5))
            player.setGameMode(GameMode.CREATIVE)
            player.sendMessage("${playerModes[player.username]}")
        }
    }
}