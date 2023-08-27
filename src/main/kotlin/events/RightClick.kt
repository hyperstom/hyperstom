package emeraldwater.infernity.dev.events

import dispatchInventory
import emeraldwater.infernity.dev.barrelName
import emeraldwater.infernity.dev.mm
import emeraldwater.infernity.dev.playerModes
import emeraldwater.infernity.dev.playerTargets
import emeraldwater.infernity.dev.plots.PlotMode
import net.minestom.server.coordinate.Vec
import net.minestom.server.event.player.PlayerBlockInteractEvent
import net.minestom.server.instance.block.Block
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.tag.Tag

fun detectRightClick(event: PlayerBlockInteractEvent) {
    val player = event.player
    val block = event.block
    playerTargets[player.username] = event.blockPosition

    if(block.getTag(Tag.String("codeBlockType")) == "sign") {
        dispatchInventory(player)
    }

    if(block.name() == "minecraft:barrel") {
        player.openInventory(Inventory(InventoryType.CHEST_6_ROW, barrelName))
        for(slot in 0..53) {
            val compound = block.getTag(Tag.ItemStack("barrel.slot$slot"))
            if(compound != null) {
                player.openInventory?.setItemStack(slot, compound)
            }
        }
    }

    if(block.name() == "minecraft:stone" && playerModes[player.username]?.mode == PlotMode.DEV && player.isSneaking) {
        val x = event.blockPosition.x().toInt()
        val y = event.blockPosition.y().toInt()
        val z = event.blockPosition.z().toInt()+1
        val blocks = mutableListOf<Block>()
        for(sx in x-1..x+1) {
            for(sy in y..y+2) {
                for(sz in z..128) {
                    blocks.add(event.player.instance.getBlock(Vec(sx.toDouble(), sy.toDouble(), sz.toDouble())))
                    event.player.instance.setBlock(Vec(sx.toDouble(), sy.toDouble(), sz.toDouble()), Block.AIR)
                }
            }
        }
        val iter = blocks.iterator()
        for(sx in x-1..x+1) {
            for(sy in y..y+2) {
                for(sz in z..128) {
                    event.player.instance.setBlock(Vec(sx.toDouble(), sy.toDouble(), (sz+2).toDouble()), iter.next())
                }
            }
        }
    }
}