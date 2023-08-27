package emeraldwater.infernity.dev.inventories.items

import emeraldwater.infernity.dev.interpreter.IfPlayer
import emeraldwater.infernity.dev.mm
import net.minestom.server.entity.Player
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.inventory.TransactionOption
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag

private val menuItems: List<ItemStack> = listOf(
    DevItemBuilder()
        .item(Material.OAK_PRESSURE_PLATE)
        .name("<gold>Is Standing On Block")
        .description("Checks if a player is standing on something.")
        .parameterPlural(Type.BLOCK, "Blocks to check against")
        .build(IfPlayer.STANDING_ON),
)

fun displayIfPlayerMenu(player: Player) {
    val inventory = Inventory(InventoryType.CHEST_1_ROW, "If Player")
    inventory.addItemStacks(menuItems, TransactionOption.ALL)
    player.openInventory(inventory)
}