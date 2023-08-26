package emeraldwater.infernity.dev.inventories

import emeraldwater.infernity.dev.mm
import net.minestom.server.entity.Player
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.inventory.TransactionOption
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag

private val menuItems: List<ItemStack> = listOf(
    ItemStack.builder(Material.BRICK)
        .displayName(mm("<red>="))
        .lore(
            mm("<gray>Set a variable to a new value."),
            mm(""),
            mm("<white>Chest Parameters:"),
            mm("<yellow>Variable <dark_gray>- <gray>Variable to define"),
            mm("<yellow>Any Value <dark_gray>- <gray>Value to assign to the variable"),
        )
        .build()
        .withTag(Tag.String("changeSign"), "="),
)

fun displaySetVariableMenu(player: Player) {
    val inventory = Inventory(InventoryType.CHEST_1_ROW, "Player Event")
    inventory.addItemStacks(menuItems, TransactionOption.ALL)
    player.openInventory(inventory)
}