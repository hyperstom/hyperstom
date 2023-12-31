package emeraldwater.infernity.dev.plots

import emeraldwater.infernity.dev.instanceHub
import emeraldwater.infernity.dev.interpreter.*
import emeraldwater.infernity.dev.items.DevItems
import emeraldwater.infernity.dev.playerInterpreter
import emeraldwater.infernity.dev.playerModes
import kotlinx.coroutines.*
import net.hollowcube.polar.PolarLoader
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.GameMode
import net.minestom.server.entity.Player
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.block.Block
import net.minestom.server.world.DimensionType
import java.io.File
import java.lang.IllegalArgumentException
import java.util.concurrent.CompletableFuture
import kotlin.io.path.Path

var plots = mutableListOf<Plot>()

data class Plot(val id: Int) {
    var buildInstance: InstanceContainer
    var devInstance: InstanceContainer
    var devActionContainers: List<ActionContainer> = listOf()
    val plotScope = CoroutineScope(Dispatchers.Default)
    init {
        val instanceManager = MinecraftServer.getInstanceManager()
        buildInstance = instanceManager.createInstanceContainer(DimensionType.OVERWORLD)
        devInstance = instanceManager.createInstanceContainer(DimensionType.OVERWORLD)
        val buildWorldFile = File("./worlds/build-$id.polar")
        if (!buildWorldFile.exists()) {
            buildInstance.chunkLoader = PolarLoader(Path("./worlds/build-$id.polar"))
            for (x in 0..128) {
                for (z in 0..128) {
                    buildInstance.setBlock(x, 50, z, Block.GRASS_BLOCK)
                    if (x == 0 || z == 0 || x == 128 || z == 128) {
                        buildInstance.setBlock(x, 50, z, Block.WHITE_WOOL)
                    }
                }
            }
        }
        val devWorldFile = File("./worlds/dev-$id.polar")
        if (!devWorldFile.exists()) {
            devInstance.chunkLoader = PolarLoader(Path("./worlds/dev-$id.polar"))
            for (x in -0 downTo -20 step 3) {
                for (z in 0..128) {
                    for (y in 1..255 step 5) {
                        devInstance.setBlock(x, y, z, Block.WHITE_STAINED_GLASS)
                    }
                }
            }
            for (x in -0 downTo -20) {
                for (z in 0..128) {
                    devInstance.setBlock(x, 1, z, Block.WHITE_STAINED_GLASS)
                    if (x % 3 != 0) {
                        devInstance.setBlock(x, 1, z, Block.LIGHT_GRAY_STAINED_GLASS)
                    }
                }

            }
        }

        buildInstance.chunkLoader = PolarLoader(Path("./worlds/build-$id.polar"))
        buildInstance.worldBorder.centerX = 64.0f
        buildInstance.worldBorder.centerZ = 64.0f
        buildInstance.worldBorder.diameter = 128.0
        buildInstance.worldBorder.warningBlocks = 0
        buildInstance.worldBorder.warningTime = 0


        devInstance.chunkLoader = PolarLoader(Path("./worlds/dev-$id.polar"))

        plots.add(this@Plot)

        plotScope.launch {
            savePlot()
        }
    }

    suspend fun savePlot() {
        var iter = 0
        for(sx in 1 downTo -3) {
            for(sz in -1..10) {
                withContext(Dispatchers.IO) {
                    devInstance.loadChunk(sx, sz).get()
                }
            }
        }


        devActionContainers = parseDevArea(devInstance)

        while(true) {
            iter++
            try {
                devActionContainers = parseDevArea(devInstance)
            } catch(_: Exception) {}

            if(iter%4 == 0) {
                buildInstance.saveChunksToStorage()
                buildInstance.saveInstance()
                devInstance.saveChunksToStorage()
                devInstance.saveInstance()
            }



            delay(2000L)
        }
    }
    fun joinInstance(player: Player) {
        player.sendMessage("Taking you to plot ID #$id...")
        try {
            player.setInstance(instanceHub).thenRun {
                player.setInstance(buildInstance).thenRun {
                    playerModes[player.username] = PlotState(id, PlotMode.PLAY)
                    player.sendMessage("You have joined plot ID #$id")
                    player.teleport(Pos(0.5, 52.0, 0.5))
                    player.setGameMode(GameMode.SURVIVAL)
                    player.inventory.clear()

                    playerInterpreter[player.username] = Interpreter(
                        devActionContainers,
                        listOf(player),
                        id,
                        buildInstance
                    )
                    player.interpret(PlayerEvent.JOIN)
                }
            }
        } catch(e: IllegalArgumentException) {
            player.setInstance(buildInstance).thenRun {
                playerModes[player.username] = PlotState(id, PlotMode.PLAY)
                player.sendMessage("You have joined plot ID #$id")
                player.teleport(Pos(0.5, 52.0, 0.5))
                player.setGameMode(GameMode.SURVIVAL)
                player.inventory.clear()

                playerInterpreter[player.username] = Interpreter(
                    devActionContainers,
                    listOf(player),
                    id,
                    buildInstance
                )
                player.interpret(PlayerEvent.JOIN)
            }
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
            player.inventory.clear()
            player.inventory.setItemStack(8, DevItems.valuesItem)
            player.inventory.setItemStack(7, DevItems.codeBlocksItem)
        }
    }
}