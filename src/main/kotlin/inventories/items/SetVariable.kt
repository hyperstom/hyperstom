package emeraldwater.infernity.dev.inventories.items

import emeraldwater.infernity.dev.interpreter.SetTarget
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
        .item(Material.BRICK)
        .name("<red>Set Variable (=)")
        .description("<gray>Sets a variable to a value you define")
        .parameter(Type.VARIABLE, "Variable to set")
        .parameter(Type.ANY_VALUE, "Value to set")
        .build(SetTarget.DEFAULT)
)

fun displaySetVariableMenu(player: Player) {
    val inventory = Inventory(InventoryType.CHEST_1_ROW, "Player Event")
    inventory.addItemStacks(menuItems, TransactionOption.ALL)
    player.openInventory(inventory)
}