package emeraldwater.infernity.dev.inventories.items

import emeraldwater.infernity.dev.interpreter.PlayerAction
import emeraldwater.infernity.dev.mm
import net.minestom.server.entity.Player
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.inventory.TransactionOption
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.network.packet.client.ClientPacketsHandler.Play
import net.minestom.server.tag.Tag

private val menuItems: List<ItemStack> = listOf(
//    ItemStack.builder(Material.POTATO)
//        .lore(
//            mm("<gray>Send a message to a player."),
//            mm(""),
//            mm("<white>Chest Parameters:"),
//            mm("<aqua>Rich Text <dark_gray>- <gray>Message to send to the player"),
//        )
//        .displayName(mm("<!italic><green>Send Message"))
//        .build()
//        .withTag(Tag.String("changeSign"), PlayerAction.SEND_MESSAGE.signText),

    DevItemBuilder()
        .item(Material.BOOK)
        .name("<green>Send Message")
        .description("Send a message to the player.")
        .parameter(Type.RICH_TEXT, "Message to send")
        .build(PlayerAction.SEND_MESSAGE),

    DevItemBuilder()
        .item(Material.BOOK)
        .name("<green>Send Actionbar")
        .description("Send a message to the player\nthrough the actionbar.")
        .parameter(Type.RICH_TEXT, "Message to send")
        .build(PlayerAction.SEND_ACTIONBAR),

    DevItemBuilder()
        .item(Material.PISTON)
        .name("<green>Launch Up")
        .description("Launch the player upwards.")
        .parameter(Type.NUMBER, "Amount to launch up by")
        .build(PlayerAction.LAUNCH_UP),

    DevItemBuilder()
        .item(Material.OAK_HANGING_SIGN)
        .name("<green>Send Title")
        .description("Sends the player a title.")
        .parameter(Type.RICH_TEXT, "Title to send")
        .parameter(Type.RICH_TEXT, "Subtitle to send")
        .build(PlayerAction.LAUNCH_UP)
)

fun displayPlayerActionMenu(player: Player) {
    val inventory = Inventory(InventoryType.CHEST_1_ROW, "Player Action")
    inventory.addItemStacks(menuItems, TransactionOption.ALL)
    player.openInventory(inventory)
}