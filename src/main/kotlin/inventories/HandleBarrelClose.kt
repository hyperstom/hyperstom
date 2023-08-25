package emeraldwater.infernity.dev.inventories

import emeraldwater.infernity.dev.barrelName
import emeraldwater.infernity.dev.playerTargets
import net.minestom.server.event.inventory.InventoryCloseEvent
import net.minestom.server.tag.Tag

fun handleBarrelClose(event: InventoryCloseEvent) {
    val player = event.player
    val inv = event.inventory
    if(inv?.title?.contains(barrelName) == true) {
        for(slot in 0..53) {
            val item = inv.getItemStack(slot)
            val target = playerTargets[player.username]!!
            val block = player.instance.getBlock(target)
            player.instance.setBlock(target, block.withTag(Tag.ItemStack("barrel.slot$slot"), item))
        }
    }
}