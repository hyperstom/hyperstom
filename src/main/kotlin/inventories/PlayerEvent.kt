package emeraldwater.infernity.dev.inventories

import emeraldwater.infernity.dev.mm
import net.kyori.adventure.text.minimessage.MiniMessage
import net.minestom.server.entity.Player
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.inventory.TransactionOption
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag
import org.jglrxavpok.hephaistos.nbt.NBT

private val menuItems: List<ItemStack> = listOf(
    ItemStack.builder(Material.POTATO)
        .lore(
            mm("<!italic><gray>Called when a player joins a plot.")
        )
        .displayName(mm("<!italic><green>Player Join Event"))
        .build()
        .withTag(Tag.String("changeSign"), "Join"),

    ItemStack.builder(Material.POISONOUS_POTATO)
        .lore(
            mm("<!italic><gray>Called when a player leaves a plot.")
        )
        .displayName(mm("<!italic><blue>Player Leave Event"))
        .build()
        .withTag(Tag.String("changeSign"), "Leave")
)

fun displayPlayerEventMenu(player: Player) {
    val inventory = Inventory(InventoryType.CHEST_1_ROW, "Player Event")
    inventory.addItemStacks(menuItems, TransactionOption.ALL)
    player.openInventory(inventory)
}