package emeraldwater.infernity.dev.inventories.items

import emeraldwater.infernity.dev.interpreter.PlayerEvent
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
        .description("<!italic><gray>Called when a player joins a plot.")
        .name("<!italic><green>Player Join")
        .build(PlayerEvent.JOIN),

    DevItemBuilder()
        .item(Material.POISONOUS_POTATO)
        .description("<!italic><gray>Called when a player leaves a plot.")
        .name("<!italic><green>Player Leave")
        .build(PlayerEvent.JOIN),

    DevItemBuilder()
        .item(Material.EMERALD)
        .description("<gray>Called every tick for a player.")
        .name("<green>Player Tick")
        .build(PlayerEvent.TICK),

    DevItemBuilder()
        .item(Material.LEATHER_BOOTS)
        .description("<gray>Called when the player sneaks.")
        .name("<green>Player Sneak")
        .build(PlayerEvent.SNEAK),

    DevItemBuilder()
        .item(Material.ARMOR_STAND)
        .description("<gray>Called when the player swaps hands.")
        .name("<green>Swap Hands")
        .build(PlayerEvent.SWAP_HANDS),
)

fun displayPlayerEventMenu(player: Player) {
    val inventory = Inventory(InventoryType.CHEST_1_ROW, "Player Event")
    inventory.addItemStacks(menuItems, TransactionOption.ALL)
    player.openInventory(inventory)
}