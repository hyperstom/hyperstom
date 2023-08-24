package emeraldwater.infernity.dev.plots

import emeraldwater.infernity.dev.playerModes
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Player
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.block.Block

var plots = mutableListOf<Plot>()

data class Plot(val id: Int) {
    val instanceContainer: InstanceContainer

    init {
        val instanceManager = MinecraftServer.getInstanceManager()
        instanceContainer = instanceManager.createInstanceContainer()
        for(x in 0..50) {
            for(z in 0..50) {
                instanceContainer.setBlock(x, 50, z, Block.GRASS_BLOCK)
                if(x == 0 || z == 0 || x == 50 || z == 50) {
                    instanceContainer.setBlock(x, 50, z, Block.WHITE_WOOL)
                }
            }
        }

        for(x in -1000 downTo -1020 step 4) {
            for(z in 0..50) {
                for(y in 1..255 step 10) {
                    instanceContainer.setBlock(x, y, z, Block.WHITE_STAINED_GLASS)
                }
            }
        }
        for(x in -1000 downTo -1020) {
            for(z in 0..50) {
                instanceContainer.setBlock(x, 1, z, Block.WHITE_STAINED_GLASS)
            }
        }
        plots.add(this)
    }

    fun joinInstance(player: Player) {
        player.sendMessage("Taking you to plot ID #$id...")
        val future = player.setInstance(instanceContainer)
        playerModes[player.username] = PlotState(id, PlotMode.PLAY)
        future.thenRun {
            player.sendMessage("You have joined plot ID #$id")
            player.teleport(Pos(0.5, 52.0, 0.5))
        }

    }
}