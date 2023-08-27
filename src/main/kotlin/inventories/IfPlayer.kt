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
    ItemStack.builder(Material.OAK_PRESSURE_PLATE)
        .lore(
            mm("<gray>Checks if a player is standing on a block."),
            mm(""),
            mm("<white>Chest Parameters:"),
            mm("<yellow>Block[s] <dark_gray>- <gray>The list of block[s] to check against."),
        )
        .displayName(mm("<!italic><gold>Is Standing on Block"))
        .build()
        .withTag(Tag.String("changeSign"), "StandingOn"),
)

fun displayIfPlayerMenu(player: Player) {
    val inventory = Inventory(InventoryType.CHEST_1_ROW, "If Player")
    inventory.addItemStacks(menuItems, TransactionOption.ALL)
    player.openInventory(inventory)
}