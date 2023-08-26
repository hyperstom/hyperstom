import emeraldwater.infernity.dev.mm
import net.minestom.server.entity.Player
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.inventory.TransactionOption
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag

private val menuItems: List<ItemStack> = listOf(
    ItemStack.builder(Material.POTATO)
        .displayName(mm("<!italic><green>Default Player"))
        .lore(
            mm("<!italic><gray>Targets the default player of this event.")
        )
        .build()
        .withTag(Tag.String("changeSign"), "Default"),
)

fun displaySetTargetMenu(player: Player) {
    val inventory = Inventory(InventoryType.CHEST_1_ROW, "Set Target")
    inventory.addItemStacks(menuItems, TransactionOption.ALL)
    player.openInventory(inventory)
}