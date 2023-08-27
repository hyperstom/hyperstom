import emeraldwater.infernity.dev.interpreter.SetTarget
import emeraldwater.infernity.dev.inventories.items.DevItemBuilder
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
        .item(Material.POTATO)
        .name("<green>Default Player")
        .description("<gray>Targets the default player of this event.")
        .build(SetTarget.DEFAULT)
)

fun displaySetTargetMenu(player: Player) {
    val inventory = Inventory(InventoryType.CHEST_1_ROW, "Set Target")
    inventory.addItemStacks(menuItems, TransactionOption.ALL)
    player.openInventory(inventory)
}