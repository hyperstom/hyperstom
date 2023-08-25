package emeraldwater.infernity.dev.events

import dispatchInventory
import emeraldwater.infernity.dev.barrelName
import emeraldwater.infernity.dev.mm
import emeraldwater.infernity.dev.playerTargets
import net.minestom.server.event.player.PlayerBlockInteractEvent
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
        for(slot in 1..54) {
            val compound = block.getTag(Tag.ItemStack("barrel.slot$slot"))
            if(compound != null) {
                player.openInventory?.setItemStack(slot, compound)
            }
        }
    }
}