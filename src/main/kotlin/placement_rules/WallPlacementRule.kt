package emeraldwater.infernity.dev.placement_rules

import net.minestom.server.coordinate.Point
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import net.minestom.server.instance.block.Block
import net.minestom.server.instance.block.BlockFace
import net.minestom.server.instance.block.rule.BlockPlacementRule
import java.util.Map


class WallPlacementRule(block: Block) : BlockPlacementRule(block) {
    override fun blockUpdate(updateState: UpdateState): Block {
        val blockPosition = updateState.blockPosition
        val instance = updateState.instance as Instance
        val x = blockPosition.blockX()
        val y = blockPosition.blockY()
        val z = blockPosition.blockZ()
        var east = "none"
        var north = "none"
        var south = "none"
        val up = "true"
        val waterlogged = "false"
        var west = "none"
        if (isBlock(instance, x + 1, y, z)) {
            east = "low"
        }
        if (isBlock(instance, x - 1, y, z)) {
            west = "low"
        }
        if (isBlock(instance, x, y, z + 1)) {
            south = "low"
        }
        if (isBlock(instance, x, y, z - 1)) {
            north = "low"
        }
        return block.withProperties(
            Map.of(
                "east", east,
                "north", north,
                "south", south,
                "west", west,
                "up", up,
                "waterlogged", waterlogged
            )
        )
    }

    override fun blockPlace(
        placementState: PlacementState
    ): Block {
        return block
    }

    private fun isBlock(instance: Instance, x: Int, y: Int, z: Int): Boolean {
        return instance.getBlock(x, y, z).isSolid
    }
}