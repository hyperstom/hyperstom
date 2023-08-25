package emeraldwater.infernity.dev.placement_rules

import net.minestom.server.coordinate.Point
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import net.minestom.server.instance.block.Block
import net.minestom.server.instance.block.BlockFace
import net.minestom.server.instance.block.rule.BlockPlacementRule
import net.minestom.server.utils.block.BlockUtils
import java.util.Map


class RedstonePlacementRule : BlockPlacementRule(Block.REDSTONE_WIRE) {
    override fun blockUpdate(updateState: UpdateState): Block {
        val blockUtils = BlockUtils(updateState.instance, updateState.blockPosition)
        var east = "none"
        var north = "none"
        val power = "0"
        var south = "none"
        var west = "none"

        // TODO Block should have method isRedstone, as redstone connects to more than itself.
        val blockNorth = blockUtils.north()
        val blockSouth = blockUtils.south()
        val blockEast = blockUtils.east()
        val blockWest = blockUtils.west()
        var connected = 0
        if (blockNorth.equals(Block.REDSTONE_WIRE) || blockNorth.below().equals(Block.REDSTONE_WIRE)) {
            connected++
            north = "side"
        }
        if (blockSouth.equals(Block.REDSTONE_WIRE) || blockSouth.below().equals(Block.REDSTONE_WIRE)) {
            connected++
            south = "side"
        }
        if (blockEast.equals(Block.REDSTONE_WIRE) || blockEast.below().equals(Block.REDSTONE_WIRE)) {
            connected++
            east = "side"
        }
        if (blockWest.equals(Block.REDSTONE_WIRE) || blockWest.below().equals(Block.REDSTONE_WIRE)) {
            connected++
            west = "side"
        }
        if (blockNorth.above().equals(Block.REDSTONE_WIRE)) {
            connected++
            north = "up"
        }
        if (blockSouth.above().equals(Block.REDSTONE_WIRE)) {
            connected++
            south = "up"
        }
        if (blockEast.above().equals(Block.REDSTONE_WIRE)) {
            connected++
            east = "up"
        }
        if (blockWest.above().equals(Block.REDSTONE_WIRE)) {
            connected++
            west = "up"
        }
        if (connected == 0) {
            north = "side"
            south = "side"
            east = "side"
            west = "side"
        } else if (connected == 1) {
            if (north != "none") {
                south = "side"
            }
            if (south != "none") {
                north = "side"
            }
            if (east != "none") {
                west = "side"
            }
            if (west != "none") {
                east = "side"
            }
        }

        // TODO power
        return Block.REDSTONE_WIRE.withProperties(
            Map.of(
                "east", east,
                "north", north,
                "south", south,
                "west", west,
                "power", power
            )
        )
    }

    override fun blockPlace(
        placementState: PlacementState
    ): Block? {
        val belowBlock = placementState.instance.getBlock(placementState.placePosition.sub(0.0, 1.0, 0.0))
        return if (belowBlock.isSolid) block else null
    }
}