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
    ItemStack.builder(Material.POTATO)
        .lore(
            mm("<gray>Send a message to a player."),
            mm(""),
            mm("<white>Chest Parameters:"),
            mm("<aqua>Text <dark_gray>- <gray>Message to send to the player"),
        )
        .displayName(mm("<!italic><green>Send Message"))
        .build()
        .withTag(Tag.String("changeSign"), "SendMessage"),
    ItemStack.builder(Material.STICK)
        .lore(
            mm("<gray>Launch the player up."),
            mm(""),
            mm("<white>Chest Parameters:"),
            mm("<red>Number <dark_gray>- <gray>Amount to launch them up by."),
        )
        .displayName(mm("<!italic><green>Launch Up"))
        .build()
        .withTag(Tag.String("changeSign"), "LaunchUp"),
)

fun displayPlayerActionMenu(player: Player) {
    val inventory = Inventory(InventoryType.CHEST_1_ROW, "Player Action")
    inventory.addItemStacks(menuItems, TransactionOption.ALL)
    player.openInventory(inventory)
}