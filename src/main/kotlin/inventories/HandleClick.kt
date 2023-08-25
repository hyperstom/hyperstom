package emeraldwater.infernity.dev.inventories

import emeraldwater.infernity.dev.events.signWithLines
import emeraldwater.infernity.dev.playerTargets
import net.kyori.adventure.sound.Sound
import net.minestom.server.entity.Player
import net.minestom.server.event.inventory.InventoryClickEvent
import net.minestom.server.event.inventory.PlayerInventoryItemChangeEvent
import net.minestom.server.item.ItemStack
import net.minestom.server.tag.Tag

fun onClickItem(event: InventoryClickEvent) {
    val item = event.clickedItem
    val player = event.player
    if(item.hasTag(Tag.String("changeSign"))) {
        val changeTo = item.getTag(Tag.String("changeSign"))!!
        player.closeInventory()
        val target = playerTargets[player.username]!!
        val block = player.instance.getBlock(target)
        if(block.getTag(Tag.String("codeBlockType")) == "sign") {
            val line1 = block.getTag(Tag.String("line1"))
            player.instance.setBlock(playerTargets[player.username]!!, signWithLines(line1, changeTo))

        }
    }
}

