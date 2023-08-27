package emeraldwater.infernity.dev.items

import emeraldwater.infernity.dev.mm
import net.minestom.server.event.player.PlayerUseItemEvent
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.inventory.TransactionOption

fun checkValuesMenu(event: PlayerUseItemEvent) {
    if(event.itemStack == DevItems.valuesItem) {
        event.player.openInventory(Inventory(InventoryType.CHEST_1_ROW, mm("Item Values")))
        event.player.openInventory?.addItemStacks(
            listOf(
                DevItems.text,
                DevItems.richText,
                DevItems.number,
                DevItems.variable,
                DevItems.func,
                DevItems.debugStick,
            ),
            TransactionOption.ALL
        )
    }
    if(event.itemStack == DevItems.codeBlocksItem) {
        event.player.openInventory(Inventory(InventoryType.CHEST_1_ROW, mm("Code Blocks")))
        event.player.openInventory?.addItemStacks(
            listOf(
                DevItems.playerEvent,
                DevItems.playerAction,
                DevItems.setVariable,
                DevItems.setTarget,
                DevItems.function,
                DevItems.ifPlayer,
            ),
            TransactionOption.ALL
        )
    }
}