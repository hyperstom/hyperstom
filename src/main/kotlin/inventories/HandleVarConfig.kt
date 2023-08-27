package emeraldwater.infernity.dev.inventories

import emeraldwater.infernity.dev.items.handleVarLore
import emeraldwater.infernity.dev.mm
import net.minestom.server.entity.Player
import net.minestom.server.event.inventory.InventoryPreClickEvent
import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag

private object ConfigItems {
    object Mutability {
        val mutable = ItemStack.builder(Material.ITEM_FRAME)
            .displayName(mm("<#ffd42a>Mutable"))
            .lore(
                mm("<gray>This variable can be changed after it's"),
                mm("<gray>defined."),
                mm(""),
                mm("<dark_aqua>» <aqua>Mutable"),
                mm("<dark_grey>» <grey>Immutable"),
            )
            .build()
        val immutable = ItemStack.builder(Material.ITEM_FRAME)
            .displayName(mm("<#ff5500>Immutable"))
            .lore(
                mm("<gray>This variable can not be changed after it's"),
                mm("<gray>defined."),
                mm(""),
                mm("<dark_grey>» <grey>Mutable"),
                mm("<dark_aqua>» <aqua>Immutable"),

            )
            .build()
    }
    object Lifetime {
        val global = ItemStack.builder(Material.ITEM_FRAME)
            .displayName(mm("<green>Global"))
            .lore(
                mm("<gray>This variable is global to the whole"),
                mm("<gray>plot."),
                mm(""),
                mm("<dark_grey>» <grey>Block"),
                mm("<dark_aqua>» <aqua>Global"),
                mm("<dark_grey>» <grey>Persistent"),
            )
            .build()

        val block = ItemStack.builder(Material.ITEM_FRAME)
            .displayName(mm("<aqua>Block"))
            .lore(
                mm("<gray>This variable is local to this block."),
                mm(""),
                mm("<dark_aqua>» <aqua>Block"),
                mm("<dark_grey>» <grey>Global"),
                mm("<dark_grey>» <grey>Persistent"),
            )
            .build()

        val persistent = ItemStack.builder(Material.ITEM_FRAME)
            .displayName(mm("<yellow>Persistent"))
            .lore(
                mm("<gray>This variable is global & saves"),
                mm("<gray>when everyone leaves the plot."),
                mm(""),
                mm("<dark_grey>» <grey>Block"),
                mm("<dark_grey>» <grey>Global"),
                mm("<dark_aqua>» <aqua>Persistent"),
            )
            .build()
    }
}
fun openVarMenu(player: Player) {
    player.openInventory(Inventory(InventoryType.CHEST_3_ROW, "Variable Configuration"))
    val mutability = player.itemInMainHand.getTag(Tag.String("varitem.value.mutable"))
    val lifetime = player.itemInMainHand.getTag(Tag.String("varitem.value.lifetime"))
    // slot 12 lifetime
    if(mutability == "true") {
        player.openInventory?.setItemStack(
            12,
            ConfigItems.Mutability.mutable
        )
    } else {
        player.openInventory?.setItemStack(
            12,
            ConfigItems.Mutability.immutable
        )
    }

    // slot 14 mutability
    if(lifetime == "global") {
        player.openInventory?.setItemStack(
            14,
            ConfigItems.Lifetime.global
        )
    }
    if(lifetime == "block") {
        player.openInventory?.setItemStack(
            14,
            ConfigItems.Lifetime.block
        )
    }
    if(lifetime == "persistent") {
        player.openInventory?.setItemStack(
            14,
            ConfigItems.Lifetime.persistent
        )
    }
}

fun clickVarItem(event: InventoryPreClickEvent) {
    val player = event.player
    var clicked = false
    val item = player.itemInMainHand
    var lifetime = player.itemInMainHand.getTag(Tag.String("varitem.value.lifetime"))
    var mutability = player.itemInMainHand.getTag(Tag.String("varitem.value.mutable"))
    val value = player.itemInMainHand.getTag(Tag.String("varitem.value.value"))
    if(event.clickedItem == ConfigItems.Mutability.mutable) {
        event.isCancelled = true
        clicked = true
        mutability = "false"


        player.openInventory?.setItemStack(
            12,
            ConfigItems.Mutability.immutable
        )
    }
    if(event.clickedItem == ConfigItems.Mutability.immutable) {
        event.isCancelled = true
        mutability = "true"
        clicked = true

        player.openInventory?.setItemStack(
            12,
            ConfigItems.Mutability.mutable
        )
    }

    if(event.clickedItem == ConfigItems.Lifetime.block) {
        event.isCancelled = true
        lifetime = "global"
        clicked = true

        player.openInventory?.setItemStack(
            14,
            ConfigItems.Lifetime.global
        )
    }

    if(event.clickedItem == ConfigItems.Lifetime.persistent) {
        event.isCancelled = true
        lifetime = "block"
        clicked = true

        player.openInventory?.setItemStack(
            14,
            ConfigItems.Lifetime.block
        )
    }

    if(event.clickedItem == ConfigItems.Lifetime.global) {
        event.isCancelled = true
        lifetime = "persistent"
        clicked = true

        player.openInventory?.setItemStack(
            14,
            ConfigItems.Lifetime.persistent
        )
    }

    if(clicked) {
        player.itemInMainHand = item
            .withDisplayName(mm("<yellow>$value"))
            .withLore(listOf(handleVarLore(lifetime, mutability)))
            .withTag(Tag.String("varitem.value"), value)
            .withTag(Tag.String("varitem.mutable"), mutability)
            .withTag(Tag.String("varitem.lifetime"), lifetime)
    }
}