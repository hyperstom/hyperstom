package emeraldwater.infernity.dev.placement_rules

import net.minestom.server.coordinate.Point
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import net.minestom.server.instance.block.Block
import net.minestom.server.instance.block.BlockFace
import net.minestom.server.instance.block.rule.BlockPlacementRule


class AxisPlacementRule(block: Block) : BlockPlacementRule(block) {
    override fun blockUpdate(updateState: UpdateState): Block {
        return block
    }

    override fun blockPlace(placementState: PlacementState): Block {
        val blockFace = placementState.blockFace
        var axis = "y"
        if (blockFace == BlockFace.WEST || blockFace == BlockFace.EAST) {
            axis = "x"
        } else if (blockFace == BlockFace.SOUTH || blockFace == BlockFace.NORTH) {
            axis = "z"
        }
        return block.withProperty("axis", axis)
    }
}